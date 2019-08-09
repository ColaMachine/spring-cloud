    package com.dozenx.common.util;

    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.net.InetSocketAddress;
    import java.net.Socket;
    import java.net.SocketAddress;
    import java.net.URL;

    public class ZSocketHttpPost {  
      
        private final String END = "\r\n";  
        private Socket client;  
        private InputStream is;  
        private OutputStream os;  
        private String host;  
        private String path;  
          
        public ZSocketHttpPost(String url) {  
            try {  
                init(url);  
            } catch(Exception e) {  
                e.printStackTrace();  
            }  
        }  
        private void init(String url) throws Exception {  
            URL temp = new URL(url);  
            this.host = temp.getHost();  
            this.path = temp.getPath();  
            int port = temp.getPort(); 
            if(port==-1){
                port=80;
            }
           // System.out.println("port:"+port);
            client = new Socket();  
            SocketAddress address = new InetSocketAddress(this.host, port);  
            client.connect(address, 5000);  
            client.setSoTimeout(10000);  
            is = client.getInputStream();  
            os = client.getOutputStream();  
        }  
        public String post(String data) throws IOException {  
           // String data = "data=[[\"d0:22:be:c8:59:c3\",\"d0:22:be:c8:59:c3\",\"15810527689\",1453441451]]";  
            StringBuffer sb = new StringBuffer();  
            sb.append("POST "+ this.path +" HTTP/1.1" + END);  
            sb.append("Host: " + this.host + END);  
            sb.append("User-Agent:Mozilla/4.0(compatible;MSIE6.0;Windows NT 5.0)" + END);  
            sb.append("Accept-Language:zh-cn" + END);  
            sb.append("Accept-Encoding:deflate" + END);  
            sb.append("Accept:*/*" + END);  
            sb.append("Connection:Keep-Alive" + END);  
            sb.append("Content-Type: application/x-www-form-urlencoded" + END);  
            sb.append("Content-Length: "+ data.length() + END);  
            sb.append(END);  
            sb.append(data);  
            String requestString = sb.toString();  
            //System.out.println(requestString);  
            os.write(requestString.getBytes());  
            os.flush();  
            byte[] rBuf = new byte[1024];  
            int i = is.read(rBuf);  
            //System.out.println(new String(rBuf));  
            return new String(rBuf);
        }  
        public static void main(String[] args) throws IOException {  
            String url = "https://swifi.cnzz.com/portallog";  
            ZSocketHttpPost post = new ZSocketHttpPost(url);
            String data = "data=[[\"d0:22:be:c8:59:c3\",\"d0:22:be:c8:59:c3\",\"15810527689\",1453441451]]";
            post.post(data);  
        }  
      
    }  