package logo.vvguoliang.com.logo.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by liuyouxi on 2017/1/20.
 * Image工具类
 */

public class ImageUtils {
	
	public static final int CROP_CENTER = 0;

	public static final int CROP_LEFT = 1;
	
	public static final int CROP_RIGHT = 2;
	
	public static void remove(Context context, String path) {
		File file = new File(path);

		if (file.exists()) {
			File newFile = new File(path + System.currentTimeMillis());
			file.renameTo(newFile);
			file.delete();
		}
	}

    public static String combine(String path, String name) {
        if (path.endsWith("/")) {
            return path + name;
        }
        return path + "/" + name;
    }

	/**
	 * 保存图片
	 * @param context
	 * @param path 文件目录
	 * @param name 文件名
	 * @param bmp 图片
	 * @param quality 图片质量
	 * @param format 图片格式
	 * @return 保存成功的图片路径，失败返回null
	 */
	public static String save(Context context, String path, String name,
							  Bitmap bmp, int quality, Bitmap.CompressFormat format) {
		
		boolean ret = false;
		File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(combine(path, name));

		FileOutputStream out;

		try {
			out = new FileOutputStream(file);
			ret = bmp.compress(format, quality, out);
			out.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		if (ret) {
			return file.getPath();
		}
		return null;
	}

	public static Bitmap changeColor(Bitmap bitmap, int color) {
		if(bitmap ==  null)
			return null;
		
		if (!bitmap.isMutable()) {
			if (bitmap.getConfig() != null) {
				bitmap = bitmap.copy(bitmap.getConfig(), true);
			} else {
				bitmap = bitmap.copy(Config.ARGB_8888, true);
			}
		}

		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int temp = bitmap.getPixel(i, j);
				if (temp != 0) {
					int alpha = Color.alpha(temp);
					bitmap.setPixel(i, j, Color.argb(alpha, red, green, blue));
				}
			}
		}

		return bitmap;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,

	int minSideLength, int maxNumOfPixels) {

		double w = options.outWidth;

		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 :

		(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 :

		(int) Math.min(Math.floor(w / minSideLength),

		Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	public static int getExifOrientation(String path) {
		int d = 0;

		ExifInterface exif = null;

		try {
			exif = new ExifInterface(path);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		if (exif != null) {
			int or = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			
			if (or != -1) {
				// We only recognize a subset of orientation tag values.
				switch (or) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					d = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					d = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					d = 270;
					break;
				}
			}
		}
		return d;
	}

	public static Bitmap loadLocalBitmap(final Context context, String filePathName) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePathName, opts);

			opts.inSampleSize = ImageUtils.computeSampleSize(opts, -1, context
					.getResources().getDisplayMetrics().widthPixels
					* context.getResources().getDisplayMetrics().heightPixels);
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(filePathName, opts);

			int d = ImageUtils.getExifOrientation(filePathName);

			if (d != 0) {
				Matrix matrix = new Matrix();

				matrix.postRotate(d);
				Bitmap tmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);

				if (tmp != bitmap){
					bitmap.recycle();
				}
				return tmp;
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static boolean copy(String source, String dir, String filename) {
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream(source);

			if (!dir.endsWith("/")) {
				dir += "/";
			}
			out = new FileOutputStream(dir + filename);

			byte[] buffer = new byte[1024];

			while (true) {
				int len = in.read(buffer);

				if (len == -1) {
					break;
				}

				out.write(buffer, 0, len);
				out.flush();
			}
			return true;
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {

			}
		}
		return false;
	}

	public static String compressImage(Context context, String path) {
		
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}
		String prefix = Environment.getExternalStorageDirectory().getPath() + "/coco/cache/opt" + System.currentTimeMillis();
		
		File file = new File(prefix);
		try {
			
			if (file.exists()) {
				file.delete();
			}
			
			file.createNewFile();
			
			Bitmap bmp = loadLocalBitmap(context, path);
			
			FileOutputStream stream = new FileOutputStream(file);
			
			bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
			
			stream.close();
			return file.getPath();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getRecentImage(Context context, int width, int height) {
		
		Cursor cursor = null;
		String path = null;

		try {
			cursor = context
					.getContentResolver()
					.query(Images.Media.EXTERNAL_CONTENT_URI,
							null, null, null,
							Images.Media.DATE_TAKEN + " DESC limit 1");
			if (cursor != null && cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(Images.Media.DATA));
				cursor.close();
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}

		if (path == null) {
			return null;
		}
		try {
			Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			return bmp;
		}
		catch (OutOfMemoryError e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static Bitmap cropToFit(Context context, Bitmap source, int dstWidth,
								   int dstHeight, int flags)  {

		if (source == null) {
			return null;
		}

		if (source.getWidth() == dstWidth && source.getHeight() == dstHeight) {
			return source;
		}

		if (dstWidth == 0) {
			dstWidth = getScreenWidth(context);
			dstHeight = getScreenHeight(context) - getStatusBarHeight(context);
		}

		int newWidth, newHeight;

		if (dstWidth * source.getHeight() <= dstHeight * source.getWidth()) {//use full height
			newHeight = source.getHeight();
			newWidth = dstWidth * newHeight / dstHeight;

		} else {//use full width
			newWidth = source.getWidth();
			newHeight = dstHeight * newWidth / dstWidth;
		}

		try {
			Bitmap bmp = Bitmap.createBitmap(dstWidth, dstHeight, Config.ARGB_8888);

			Canvas canvas = new Canvas(bmp);

			int left = (source.getWidth() - newWidth) / 2;
			int top = (source.getHeight() - newHeight) / 2;

			canvas.drawBitmap(source, new Rect(left, top, left + newWidth, top + newHeight)
					, new Rect(0, 0, dstWidth, dstHeight), null);

			canvas.save();

			return bmp;
		}catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}

	}

	public static final String ACCOUNT_LOGO_CACHE_SUFFIX = "_account_logo";

	public static final String CALENADR_LOGO_CACHE_SUFFIX = "_calendar_logo";

    public static int getStatusBarHeight(Context context) {
        try {
            @SuppressWarnings("rawtypes")
			Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");

            int id = Integer.parseInt(field.get(object).toString());
            return  context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen height
     */
    public static int getScreenHeight(Context context) {
        if (context != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            return displayMetrics.heightPixels;
        }
        else {
            return 1920;
        }
    }
    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            return displayMetrics.widthPixels;
        }
        else {
            return 1080;
        }
    }

    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.density;
    }

    public static Bitmap getRoundImage(Bitmap oriImg) {

        Bitmap targetBitmap = null;

        if (oriImg != null && !oriImg.isRecycled()) {
            int size = Math.min(oriImg.getWidth(), oriImg.getHeight());
            int targetWidth = size;
            int targetHeight = size;

            targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(targetWidth / 2, targetHeight / 2, targetWidth / 2, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(oriImg, new Rect(0, 0, size, size), new Rect(0, 0, targetWidth, targetHeight), paint);

            oriImg.recycle();
        }
        return targetBitmap;
    }
}
