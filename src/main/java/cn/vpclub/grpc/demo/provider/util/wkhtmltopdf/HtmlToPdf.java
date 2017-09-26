package cn.vpclub.grpc.demo.provider.util.wkhtmltopdf;

import java.io.File;

/**
 * HTML转PDF
 * Created by WangRui on 2017/9/22.
 */
public class HtmlToPdf {
    /**
     * wkhtmltopdf在系统中的路径
     */
    private static final String toPdfTool = "C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";

    /**
     * html转pdf
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }

        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append("  --header-line");//页眉下面的线
        cmd.append("  --header-center 这里是页眉这里是页眉这里是页眉这里是页眉 ");//页眉中间内容
        //cmd.append("  --margin-top 30mm ");//设置页面上边距 (default 10mm)
        cmd.append(" --header-spacing 10 ");//    (设置页眉和内容的距离,默认0)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
//            Process proc = Runtime.getRuntime().exec("cmd /c " + cmd.toString());//执行需要系统管理员权限时
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

//    public static void main(String[] args) {
//        HtmlToPdf.convert("http://www.cnblogs.com/xionggeclub/p/6144241.html", "E:\\workspace2017\\files\\wkhtmltopdf.pdf");
//        HtmlToPdf.convert("http://dev.apps.vpclub.io/moses/express/consumer//express/status/query?appId=123&expressCode=ems&expressNo=9786740698522", "E:\\workspace2017\\files\\findUser.pdf");
//    }
}
