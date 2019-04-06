import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Servlet Tester.
 *
 * @author <Authors name>
 * @since <pre>四月 3, 2019</pre>
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class WaitTestTest {

    private Map<String, String> parameters;
    private String result;

    @Parameterized.Parameters
    public static Collection data() {
        Map<String , String> parameters1 = new HashMap<String , String>(){{
            // 查询所有书籍模块参数
            put("name", "虚拟");
            put("msg", "0");
        }};

        Map<String , String> parameters2 = new HashMap<String , String>(){{
            // 查询某个书籍详情模块参数
            put("id", "1");
            put("msg", "1");
        }};

        Map<String , String> parameters3 = new HashMap<String , String>(){{
            // 登陆模块参数
            put("name", "hey");
            put("password", "hey123");
            put("msg", "2");
        }};

        Map<String , String> parameters4 = new HashMap<String , String>(){{
            // 借书参数
            put("id", "1");
            put("msg", "3");
        }};


        return Arrays.asList(new Object[][]{
                {"{\"address\":\"\",\"author\":\"周志明\",\"detail\":\"\",\"id\":1,\"name\":\"无法理解虚拟机\",\"press\":\"北京大出版社\",\"pressdate\":\"2019-03-06\",\"status\":\"\"}",parameters1},
                {"{\"address\":\"P0A1\",\"author\":\"\",\"detail\":\"很好\",\"id\":1,\"name\":\"无法理解虚拟机\",\"press\":\"\",\"pressdate\":\"\",\"status\":\"已出馆\"}",parameters2},
                {"1",parameters3},
                {"1",parameters4},
        });
    }

    public WaitTestTest(String result, Map<String, String> parameters) {
        // 构造对象
        this.parameters = parameters;
        this.result = result;
    }

    @Before
    public void before() throws Exception {
        System.out.println("开始测试....");

    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: doPost(HttpServletRequest request, HttpServletResponse  response)
     *
     */
    @Test
    public void testDoPost() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: doGet(HttpServletRequest request, HttpServletResponse  response)
     *
     */
    @Test
    public void testDoGet() throws Exception {
        // 断言
        assertEquals(result,sendPost(parameters));
    }


    public static String sendPost(Map<String, String> parameters) {

        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }

            String url = "http://localhost:8080/Servlet";
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;

            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
