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
 * @since <pre>���� 3, 2019</pre>
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class WaitTestTest {

    private Map<String, String> parameters;
    private String result;

    @Parameterized.Parameters
    public static Collection data() {
        Map<String , String> parameters1 = new HashMap<String , String>(){{
            // ��ѯ�����鼮ģ�����
            put("name", "����");
            put("msg", "0");
        }};

        Map<String , String> parameters2 = new HashMap<String , String>(){{
            // ��ѯĳ���鼮����ģ�����
            put("id", "1");
            put("msg", "1");
        }};

        Map<String , String> parameters3 = new HashMap<String , String>(){{
            // ��½ģ�����
            put("name", "hey");
            put("password", "hey123");
            put("msg", "2");
        }};

        Map<String , String> parameters4 = new HashMap<String , String>(){{
            // �������
            put("id", "1");
            put("msg", "3");
        }};


        return Arrays.asList(new Object[][]{
                {"{\"address\":\"\",\"author\":\"��־��\",\"detail\":\"\",\"id\":1,\"name\":\"�޷���������\",\"press\":\"�����������\",\"pressdate\":\"2019-03-06\",\"status\":\"\"}",parameters1},
                {"{\"address\":\"P0A1\",\"author\":\"\",\"detail\":\"�ܺ�\",\"id\":1,\"name\":\"�޷���������\",\"press\":\"\",\"pressdate\":\"\",\"status\":\"�ѳ���\"}",parameters2},
                {"1",parameters3},
                {"1",parameters4},
        });
    }

    public WaitTestTest(String result, Map<String, String> parameters) {
        // �������
        this.parameters = parameters;
        this.result = result;
    }

    @Before
    public void before() throws Exception {
        System.out.println("��ʼ����....");

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
        // ����
        assertEquals(result,sendPost(parameters));
    }


    public static String sendPost(Map<String, String> parameters) {

        String result = "";// ���صĽ��
        BufferedReader in = null;// ��ȡ��Ӧ������
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// �����������
        String params = "";// ����֮��Ĳ���
        try {
            // �����������
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
            // ����URL����
            java.net.URL connURL = new java.net.URL(url);
            // ��URL����
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // ����ͨ������
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // ����POST��ʽ
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // ��ȡHttpURLConnection�����Ӧ�������
            out = new PrintWriter(httpConn.getOutputStream());
            // �����������
            out.write(params);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ�����ñ��뷽ʽ
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;

            // ��ȡ���ص�����
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
