package logo.vvguoliang.com.logo.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.text.DecimalFormat;
import java.util.List;

public class Utils {
    private static final int CMNET = 4;
    private static final int CMWAP = 3;
    private static final int WIFI = 1;

    @SuppressWarnings("deprecation")
    public static int network_Identification(Context context) {
        if (null == context) {
            return -2;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isConnected()) {
                        if (0 == info[i].getType()) {
                            return 1;
                        }
                        if (1 == info[i].getType()) {
                            return 2;
                        }
                        return 3;
                    }

                }
            }
        }
        return 0;
    }

    public static int getCmnet() {
        return CMNET;
    }

    public static int getCmwap() {
        return CMWAP;
    }

    public static int getWifi() {
        return WIFI;
    }

    private Utils() {
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

//	@TargetApi(value = Build.VERSION_CODES.M)
//	public static List<String> findDeniedPermissions(Activity activity,
//			String... permission) {
//		List<String> denyPermissions = new ArrayList<String>();
//		for (String value : permission) {
//			if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
//				denyPermissions.add(value);
//			}
//		}
//		return denyPermissions;
//	}

    /**
     * 先注释，build编译报错，modify by liuyouxi
     *
     * @param object
     * @return
     */
    /*public static List<Method> findAnnotationMethods(Class clazz,
          Class<? extends Annotation> clazz1) throws Exception {
	   try {
		  List<Method> methods = new ArrayList<Method>();
		  for (Method method : clazz.getDeclaredMethods()) {
			 if (method.isAnnotationPresent(clazz1)) {
				methods.add(method);
			 }
		  }
		  return methods;
	   } catch (Exception e) {
		  throw new Exception(e);
	   }
	}*/

//	public static <A extends Annotation> Method findMethodPermissionFailWithRequestCode(
//			Class clazz, Class<A> permissionFailClass, int requestCode) {
//		for (Method method : clazz.getDeclaredMethods()) {
//			if (method.isAnnotationPresent(permissionFailClass)) {
//				if (requestCode == method.getAnnotation(PermissionFail.class)
//						.requestCode()) {
//					return method;
//				}
//			}
//		}
//		return null;
//	}
//
//	public static boolean isEqualRequestCodeFromAnntation(Method m,
//			Class clazz, int requestCode) {
//		if (clazz.equals(PermissionFail.class)) {
//			return requestCode == m.getAnnotation(PermissionFail.class)
//					.requestCode();
//		} else if (clazz.equals(PermissionSuccess.class)) {
//			return requestCode == m.getAnnotation(PermissionSuccess.class)
//					.requestCode();
//		} else {
//			return false;
//		}
//	}
//
//	public static <A extends Annotation> Method findMethodWithRequestCode(
//			Class clazz, Class<A> annotation, int requestCode) {
//		for (Method method : clazz.getDeclaredMethods()) {
//			if (method.isAnnotationPresent(annotation)) {
//				if (isEqualRequestCodeFromAnntation(method, annotation,
//						requestCode)) {
//					return method;
//				}
//			}
//		}
//		return null;
//	}
//
//	public static <A extends Annotation> Method findMethodPermissionSuccessWithRequestCode(
//			Class clazz, Class<A> permissionFailClass, int requestCode) {
//		for (Method method : clazz.getDeclaredMethods()) {
//			if (method.isAnnotationPresent(permissionFailClass)) {
//				if (requestCode == method
//						.getAnnotation(PermissionSuccess.class).requestCode()) {
//					return method;
//				}
//			}
//		}
//		return null;
//	}
    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    public static String formatNum(String str) {
        try {
            double b = Double.valueOf(str.toString());
            DecimalFormat df2 = new DecimalFormat("#,###.00");
            String str2 = df2.format(b);
            return str2.startsWith(".") ? "0" + str2 : str2;
        } catch (Exception e) {
            return str;
        }

    }

    public static String formatNumOne(String str) {
        try {
            double b = Double.valueOf(str.toString());
            DecimalFormat df2 = new DecimalFormat("#,###");
            String str2 = df2.format(b);
            return str2.startsWith(".") ? "0" + str2 : str2;
        } catch (Exception e) {
            return str;
        }

    }

    /**
     * 渠道id
     */
    public static String mChannle(int id_channle, String channle) {
        if ("AJJJBF".equals(channle)) {
            if (id_channle == 1) {
                return "安卓应用市场" + "100026";
            } else {
                return "AJJJBF";
            }
        } else if ("AJJJBG".equals(channle)) {
            if (id_channle == 1) {
                return "360软件平台" + "100027";
            } else {
                return "AJJJBG";
            }
        } else if ("AJJJBH".equals(channle)) {
            if (id_channle == 1) {
                return "百度软件开放平台" + "100028";
            } else {
                return "AJJJBH";
            }
        } else if ("AJJJBI".equals(channle)) {
            if (id_channle == 1) {
                return "豌豆荚开发者中心" + "100029";
            } else {
                return "AJJJBI";
            }
        } else if ("AJJJCJ".equals(channle)) {
            if (id_channle == 1) {
                return "木蚂蚁开发者中心" + "100030";
            } else {
                return "AJJJCJ";
            }
        } else if ("AJJJCA".equals(channle)) {
            if (id_channle == 1) {
                return "小米开发者中心" + "100031";
            } else {
                return "AJJJCA";
            }
        } else if ("AJJJCB".equals(channle)) {
            if (id_channle == 1) {
                return "华为开发者中心" + "100032";
            } else {
                return "AJJJCB";
            }
        } else if ("AJJJCC".equals(channle)) {
            if (id_channle == 1) {
                return "PP助手开发者中心" + "100033";
            } else {
                return "AJJJCC";
            }
        } else if ("AJJJCD".equals(channle)) {
            if (id_channle == 1) {
                return "安智开发者联盟" + "100034";
            } else {
                return "AJJJCD";
            }
        } else if ("AJJJCE".equals(channle)) {
            if (id_channle == 1) {
                return "OPPO商店" + "100035";
            } else {
                return "AJJJCE";
            }
        } else if ("AJJJCF".equals(channle)) {
            if (id_channle == 1) {
                return "魅族商店" + "100036";
            } else {
                return "AJJJCF";
            }
        } else if ("AJJJCG".equals(channle)) {
            if (id_channle == 1) {
                return "VIVO商店" + "100037";
            } else {
                return "AJJJCG";
            }
        } else if ("AJJJCH".equals(channle)) {
            if (id_channle == 1) {
                return "联通沃商店" + "100038";
            } else {
                return "AJJJCH";
            }
        } else if ("AJJJCI".equals(channle)) {
            if (id_channle == 1) {
                return "搜狗手机助手" + "100039";
            } else {
                return "AJJJCI";
            }
        } else if ("AJJJDJ".equals(channle)) {
            if (id_channle == 1) {
                return "应用汇" + "100040";
            } else {
                return "AJJJDJ";
            }
        } else if ("AJJJDA".equals(channle)) {
            if (id_channle == 1) {
                return "乐商店" + "100041";
            } else {
                return "AJJJDA";
            }
        } else if ("AJJJDB".equals(channle)) {
            if (id_channle == 1) {
                return "酷派" + "100042";
            } else {
                return "AJJJDB";
            }
        } else if ("AJJJDC".equals(channle)) {
            if (id_channle == 1) {
                return "应用宝" + "100043";
            } else {
                return "AJJJDC";
            }
        } else if ("AJJABF".equals(channle)) {
            if (id_channle == 1) {
                return "历趣" + "100044";
            } else {
                return "AJJABF";
            }
        } else if ("AJJACB".equals(channle)) {
            if (id_channle == 1) {
                return "机锋开发者平台" + "100045";
            } else {
                return "AJJACB";
            }
        } else if ("AJJJDH".equals(channle)) {
            if (id_channle == 1) {
                return "自然" + "100048";
            } else {
                return "AJJJDH";
            }
        } else if ("AJJBFC".equals(channle)) {
            if (id_channle == 1) {
                return "三星" + "100045";
            } else {
                return "AJJBFC";
            }
        } else if ("AJJCFD".equals(channle)) {
            if (id_channle == 1) {
                return "神马" + "100046";
            } else {
                return "AJJCFD";
            }
        } else {
            return "";
        }
    }

    private static long lastClickTime = 0;

    public synchronized static boolean isFastClick() {

        if (System.currentTimeMillis() - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = System.currentTimeMillis();
        return false;
    }

    public static boolean getZero(String data) {
        if ("0".equals(data))
            return true;
        else
            return false;
    }

    public static boolean getMonth(String data) {
        if ("月".equals(data))
            return true;
        else
            return false;
    }
}
