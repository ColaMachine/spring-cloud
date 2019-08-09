package com.dozenx.common.util;

import com.dozenx.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:27 2019/4/17
 * @Modified By:
 */
public class FastYml {
    private Logger logger = LoggerFactory.getLogger(FastYml.class);

    public static void main(String args[]) {

//        Map map = new FastYml().readAsMap(new File("G:\\workspace\\dozenx\\ui\\src\\main\\resources\\alpha\\config.yml"));
//        System.out.println(JsonUtil.toJsonString(map));
    }

    class TreeNode {
        public TreeNode parentNode;
        public List<TreeNode> childNodes;
        public int indent;
        public String key;
        public boolean isLeaf;
        public String txtVal;
        public Object value;

        public TreeNode(int indent, String key) {
            this.indent = indent;
            this.key = key;
        }

        public TreeNode(int indent, String key, String value) {
            this.indent = indent;
            this.key = key;
            this.txtVal = value;
        }

        public void addChild(TreeNode child) {

            if (childNodes == null) {
                childNodes = new ArrayList<>();
            }
            this.childNodes.add(child);
            child.parentNode = this;
        }
    }

    public Map readFileAsMap(File file) {
        TreeNode node = readAsTreeNode(file);
        Map<String, Object> map = new HashMap<>();

        for (TreeNode childNode : node.childNodes) {
            map.put(childNode.key, getNodeVal(childNode));
        }
        return map;
    }

    public Map readStreamAsMap(InputStream inputStream) {
        TreeNode node = readStreamAsTreeNode(inputStream);
        Map<String, Object> map = new HashMap<>();

        for (TreeNode childNode : node.childNodes) {
            map.put(childNode.key, getNodeVal(childNode));
        }
        return map;
    }


    public Object getNodeVal(TreeNode treeNode) {

        if (treeNode.isLeaf) {
            return treeNode.txtVal;
        } else {
            HashMap<String, Object> map = new HashMap<>();
            if (treeNode.childNodes == null) {
                logger.info("treeNode.childNodes==null" + treeNode.key);
                return null;
            }
            for (TreeNode childNode : treeNode.childNodes) {

                map.put(childNode.key, getNodeVal(childNode));
            }
            return map;
        }

    }

    public TreeNode readReaderAsTreeNode(BufferedReader bufferedReader) {
        TreeNode rootNode = new TreeNode(-2, "");
        try {
            String lineStr = "";
            boolean isLeaf = true;//是否是子节点

            TreeNode lastNode = rootNode;
            //String lastPropertiesKey ="";
            //String lastPropertiesValue ="";

            while ((lineStr = bufferedReader.readLine()) != null) {

                if (StringUtil.isBlank(lineStr.trim())) {
                    continue;
                }
                if (lineStr.indexOf("#") > -1) {
                    lineStr = lineStr.substring(0, lineStr.indexOf("#"));
                    if (StringUtil.isBlank(lineStr.trim())) {
                        continue;
                    }
                }
                //logger.info("read==>"+lineStr);
                //获取前面有几个空格
                int whiteSpaceNum = getWitheSpaceNumPrefix(lineStr);
                int semicolonIndex = lineStr.indexOf(":");
                String key = lineStr.substring(0, semicolonIndex).trim();
//                if (key.equals("mybatis")) {
//                    logger.debug(key);
//                }
                String value = lineStr.substring(semicolonIndex + 1).trim();
                TreeNode nowNode = new TreeNode(whiteSpaceNum, key, value);

                int indentChanged = 0;

                indentChanged = whiteSpaceNum - lastNode.indent;//如果缩进大于2 就认为有变化
                //lastIndent= whiteSpaceNum;
                if (StringUtil.isNotBlank(value)) {
                    //不允许有子节点了
                    isLeaf = true;
                    // logger.info(getStackStr(stack) +" "+key+":"+value);
                } else {
                    isLeaf = false;
                }
                nowNode.isLeaf = isLeaf;
                if (Math.abs(indentChanged) >= 2) {
                    //认为是发生了变化的
                    //否者是同级
                    if (indentChanged > 0) {//说明上一个节点不是子节点
                        lastNode.isLeaf = false;
                        lastNode.addChild(nowNode);
                    } else {// 上级 上上级 .... 小于0 的话
                        lastNode.isLeaf = true;
                        TreeNode parentNode = lastNode.parentNode;
                        while (parentNode.indent >= whiteSpaceNum) {
                            parentNode = parentNode.parentNode;
                            if (parentNode == null) {
                                logger.error("取到最高级了");
                            }
                        }
                        parentNode.addChild(nowNode);
                        //回退到之前同级的
                    }
                } else {// 同级目录
                    lastNode.isLeaf = true;
                    lastNode.parentNode.addChild(nowNode);
                }
                if (isLeaf) {
                    //logger.info(getStackStr(stack) + ":" + value);
                }
                lastNode = nowNode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rootNode;
    }

    public TreeNode readStreamAsTreeNode(InputStream inputStream) {


        try {
            //依次读取每一行的代码
            //System.out.println(obj);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            return readReaderAsTreeNode(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public TreeNode readAsTreeNode(File file) {
        try {
            //依次读取每一行的代码
            //System.out.println(obj);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return readReaderAsTreeNode(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Properties readStreamAsProperties(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            //依次读取每一行的代码
            //System.out.println(obj);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String lineStr = "";
            boolean isLeaf = true;//是否是子节点
            LinkedList<TreeNode> stack = new LinkedList<>();
            //String lastPropertiesKey ="";
            //String lastPropertiesValue ="";
            int lastIndent = 0;
            List<String> lineStrList =new ArrayList<>();

            while ((lineStr = bufferedReader.readLine()) != null) {
                lineStrList.add(lineStr);
                if (StringUtil.isBlank(lineStr.trim())) {
                    continue;
                }
                if (lineStr.indexOf("#") > -1) {
                    lineStr = lineStr.substring(0, lineStr.indexOf("#"));
                    if (StringUtil.isBlank(lineStr.trim())) {
                        continue;
                    }
                }
                //logger.info("read==>"+lineStr);
                //获取前面有几个空格
                int whiteSpaceNum = getWitheSpaceNumPrefix(lineStr);
                int fenhaoIndex = lineStr.indexOf(":");
                String key = lineStr.substring(0, fenhaoIndex).trim();
                String value = lineStr.substring(fenhaoIndex + 1).trim();
                int indentChanged = 0;
                if (stack.size() != 0) {
                    indentChanged = whiteSpaceNum - stack.peek().indent;//如果缩进大于2 就认为有变化
                } else {
                    indentChanged = 0;//如果是第一行读取的时候 statck是空的
                }
                //lastIndent= whiteSpaceNum;
                if (StringUtil.isNotBlank(value)) {
                    //不允许有子节点了
                    isLeaf = true;
                    // logger.info(getStackStr(stack) +" "+key+":"+value);
                } else {
                    isLeaf = false;
                }
                if (Math.abs(indentChanged) >= 2) {
                    //认为是发生了变化的
                    //否者是同级
                    if (indentChanged > 0) {
                        stack.push(new TreeNode(whiteSpaceNum, key));
                    } else {
                        TreeNode indentKey = stack.poll();
                        while (indentKey.indent > whiteSpaceNum) {
                            indentKey = stack.poll();
                        }
                        //回退到之前同级的
                        stack.push(new TreeNode(whiteSpaceNum, key));
                    }
                } else {//同级的
                    stack.poll();
                    stack.push(new TreeNode(whiteSpaceNum, key));
                }
                if (isLeaf) {
                    if("\"\"".equals(value)){
                        value="";
                    }else if("\'\'".equals(value)){
                        value ="";
                    }
                    properties.put(getStackStr(stack), value);
//                    logger.info(getStackStr(stack) + ":" + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public Properties readAsProperties(File file) {
        Properties properties = new Properties();
        try {
            //依次读取每一行的代码
            //System.out.println(obj);
            return readStreamAsProperties(new FileInputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getStackStr(LinkedList<TreeNode> stack) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = stack.size() - 1; i >= 0; i--) {
            stringBuffer.append(stack.get(i).key);
            if (i != 0) {
                stringBuffer.append(".");
            }
        }
        return stringBuffer.toString();
        // logger.info(stringBuffer.toString());
    }

    public int getWitheSpaceNumPrefix(String lineStr) {
        int i = 0;
        int count = 0;
        while (i <= lineStr.length() - 1 && lineStr.charAt(i) == ' ') {
            count++;
            i++;
        }
        return count;
    }
}
