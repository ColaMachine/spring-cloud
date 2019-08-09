package com.dozenx.common.util;

import com.alibaba.fastjson.JSON;
import com.dozenx.common.util.StringUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocationUtil {

	public static String  getLocationNameById(int id){

		if(idNameMap.size()==0){
			//去数据库里去获取一下


		}
		return idNameMap.get(id);
	}
	public static HashMap<Integer,String> idNameMap  =new HashMap<>();
	public static HashMap<String,Integer> nameIdMap  =new HashMap<>();

	public static HashMap<Integer,Integer>  map = new HashMap<Integer,Integer>();
	static{


		map.put(1,31);//丽水市分公司
		//以下数据是
		map.put(2,387);//丽水市分公司
		map.put(3,390);//台州市分公司
		map.put(4,385);//嘉兴市分公司
		map.put(5,388);//宁波市分公司
		map.put(6,383);//杭州市分公司
		map.put(7,391);//温州市分公司
		map.put(8,384);//湖州市分公司
		map.put(9,389);//绍兴市分公司
		map.put(10,392);//舟山市分公司
		map.put(11,393);//衢州市分公司
		map.put(12,386);//金华市分公司
		map.put(13,3275);//松阳县分公司
		map.put(14,0);//丽水未落地区域
		map.put(15,3273);//缙云县分公司
		map.put(16,3276);//云和县分公司
		map.put(17,3270);//莲都分局
		map.put(18,0);//南城分局
		map.put(19,3274);//遂昌县分公司
		map.put(20,3277);//庆元县分公司
		map.put(21,3272);//青田县分公司
		map.put(22,3278);//景宁县分公司
		map.put(23,387);//丽水市分公司本级
		map.put(24,3271);//龙泉市分公司
		map.put(25,3297);//黄岩分局
		map.put(26,3303);//天台县分公司
		map.put(27,3302);//三门县分公司
		map.put(28,3296);//椒江分局
		map.put(29,3300);//临海市分公司
		map.put(30,3299);//温岭市分公司
		map.put(31,3304);//仙居县分公司
		map.put(32,3301);//玉环县分公司
		map.put(33,390);//台州市分公司本级
		map.put(34,3298);//路桥分局
		map.put(35,0);//台州未落地区域
		map.put(36,3253);//桐乡市分公司
		map.put(37,3248);//南湖分局
		map.put(38,3250);//海宁市分公司
		map.put(39,385);//嘉兴市分公司未落地
		map.put(40,3254);//海盐县分公司
		map.put(41,385);//嘉兴市分公司本级
		map.put(42,3249);//秀洲分局
		map.put(43,3251);//嘉善县分公司
		map.put(44,3252);//平湖市分公司
		map.put(45,3286);//慈溪市分公司
		map.put(46,3283);//北仑区分公司
		map.put(47,3282);//镇海区分公司
		map.put(48,3280);//江东分局
		map.put(49,3287);//奉化市分公司
		map.put(50,1084);//高新区分局
		map.put(51,3281);//江北分局
		map.put(52,388);//宁波市分公司本级
		map.put(53,3279);//海曙分局
		map.put(54,0);//宁波未落地区域
		map.put(55,3288);//象山县分公司
		map.put(56,3289);//宁海县分公司
		map.put(57,3285);//余姚市分公司
		map.put(58,3284);//鄞州分局
		map.put(59,383);//杭州市分公司本级
		map.put(60,3240);//临安市分公司
		map.put(61,3235);//萧山区分公司
		map.put(62,0);//杭州未落地区域
		map.put(63,3236);//余杭区分公司
		map.put(64,3229);//西湖分局
		map.put(65,3241);//桐庐县分公司
		map.put(66,3239);//富阳市分公司
		map.put(67,3230);//上城分局
		map.put(68,3234);//江干分局
		map.put(69,3238);//建德市分公司
		map.put(70,3231);//下城分局
		map.put(71,3242);//淳安县分公司
		map.put(72,3232);//拱墅分局
		map.put(73,0);//下沙分局
		map.put(74,3233);//滨江分局
		map.put(75,3308);//瑞安市分公司
		map.put(76,3311);//永嘉县分公司
		map.put(77,3307);//瓯海分局
		map.put(78,3315);//泰顺县分公司
		map.put(79,3313);//苍南县分公司
		map.put(80,3310);//洞头县分公司
		map.put(81,3309);//乐清市分公司
		map.put(82,3305);//鹿城分局
		map.put(83,391);//温州市分公司本级
		map.put(84,3312);//平阳县分公司
		map.put(85,3306);//龙湾分局
		map.put(86,0);//温州未落地区域
		map.put(87,3314);//文成县分公司
		map.put(88,3246);//长兴县分公司
		map.put(89,3243);//吴兴分公司
		map.put(90,3247);//安吉县分公司
		map.put(91,3245);//德清县分公司
		map.put(92,384);//湖州市分公司本级
		map.put(93,0);//湖州未落地区域
		map.put(94,3244);//南浔分公司
		map.put(95,3293);//绍兴分公司
		map.put(96,0);//绍兴未落地区域
		map.put(97,3291);//上虞分公司
		map.put(98,3295);//诸暨分公司
		map.put(99,3292);//嵊州分公司
		map.put(100,3294);//新昌分公司
		map.put(101,0);//柯桥分公司
		map.put(102,3317);//普陀区分公司
		map.put(103,392);//舟山市分公司本级
		map.put(104,0);//舟山市本部
		map.put(105,3316);//定海分公司
		map.put(106,0);//舟山未落地区域
		map.put(107,3318);//岱山县分公司
		map.put(108,3319);//嵊泗县分公司
		map.put(109,3320);//衢州衢江分局
		map.put(110,3323);//开化分公司
		map.put(111,3324);//龙游分公司
		map.put(112,3321);//江山分公司
		map.put(113,393);//衢州分公司
		map.put(114,3320);//衢州市分公司本级
		map.put(115,0);//衢州未落地区域
		map.put(116,3322);//常山分公司
		map.put(117,3258);//金华市分公司本级
		map.put(118,3267);//武义县分公司
		map.put(119,0);//金华未落地区域
		map.put(120,3266);//永康市分公司
		map.put(121,3265);//东阳市分公司
		map.put(122,3268);//浦江县分公司
		map.put(123,3409);//义乌市分公司
		map.put(124,3269);//磐安县分公司
		map.put(125,3257);//兰溪市分公司
		map.put(126,3256);//金东区电信局
		map.put(127,3255);//婺城区电信局
		map.put(2187,0);//浙江省-其他
		map.put(2188,387);//丽水市分公司-其他
		map.put(2189,390);//台州市分公司-其他
		map.put(2190,385);//嘉兴市分公司-其他
		map.put(2191,388);//宁波市分公司-其他
		map.put(2192,383);//杭州市分公司-其他
		map.put(2193,391);//温州市分公司-其他
		map.put(2194,384);//湖州市分公司-其他
		map.put(2195,389);//绍兴市分公司-其他
		map.put(2196,392);//舟山市分公司-其他
		map.put(2197,3320);//衢州市分公司-其他
		map.put(2198,386);//金华市分公司-其他
	}

	public static String path;
	public static String sql ="SELECT * FROM awifi_alf.center_pub_area where PARENT_ID =";
	public static Long getCenterPubAreaId(Integer id){
		if(id==null)return null;

		if(map.get(id.intValue())!=null){
			return Long.valueOf(map.get(id));
		}else{
			return 0l;
		}

	}
	public static void main(String args[]){
		//getLocationJs();
		getLocationIdNameMap();
	}

	public static void getLocationIdNameMap(){
		Connection con;

		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			String sql1 ="select id,area_name from center_pub_area ;";
			String url = "jdbc:mysql://192.168.41.74:3306/awifiopms?user=root&password=root@2015";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			Map map =new HashMap();
			while (rs.next()) {

				Long id = rs.getLong(1);
				String area_name = rs.getString(2);


				System.out.println("map.put("+id+","+area_name+");");



			}


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void getLocationMap(){
		Connection con;

		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			String sql1 ="select id,crm_code,area_name from center_pub_area where id =31 or parent_id =31 or parent_id in (SELECT id FROM center_pub_area where parent_id =31);";
			String url = "jdbc:mysql://192.168.41.74:3306/awifiopms?user=root&password=root@2015";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			Map map =new HashMap();
			while (rs.next()) {

				Long id = rs.getLong(1);
				String crm_code = rs.getString(2);
				if(map.get(crm_code)!=null )
					System.out.println("有重复数据");
				map.put(crm_code, id);

				System.out.println("map.put("+crm_code+","+id+");//"+rs.getString(3));



			}


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void getLocationMap2(){
		Connection con;

		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			String sql1 ="select pubcode id,AreaId crm_code,areaname area_name from wii_crm_area where AreaId =1 or parentid =1 or parentid in (SELECT AreaId FROM wii_crm_area where parentid =1);";
			String url = "jdbc:mysql://192.168.10.183:3306/awifiopms?user=awifi2Badmin&password=awifi2B@#$";
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			Map map =new HashMap();
			while (rs.next()) {

				Long id = rs.getLong(1);
				String crm_code = rs.getString(2);
				if(map.get(crm_code)!=null )
					System.out.println("有重复数据");
				map.put(crm_code, id);

				System.out.println("idNameMap.put("+crm_code+","+id+");//"+rs.getString(3));



			}


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void writeFile(File file ,String content) throws IOException {
		try {

			//if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			//true = append file
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public static void getLocationJs(){
		Connection con;
		StringBuffer fileContent =new StringBuffer();
		Statement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			String url = "jdbc:mysql://192.168.10.88:3306/awifi_alf?user=DBcenter&password=dbcenter@2015";
			con = DriverManager.getConnection(url);
			HashMap totalMap =new HashMap();
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			LocationUtil.getListByParentId("",1l, totalMap,stmt);
			//System.out.println(JSON.toJSONString(totalMap).replaceAll(",",",\n"));

			Iterator it = totalMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				HashMap value = (HashMap) totalMap.get(key);
				System.out.println("'"+key+"':"+JSON.toJSONString(value)+",");

				fileContent.append("'"+key+"':"+JSON.toJSONString(value)+",");
				//System.out.println(key + "→" + value);
			}

			LocationUtil.writeFile(new File("c://zzw/location.js"),fileContent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void getListByParentId(String path,Long parentid,HashMap totalMap,Statement stmt)throws Exception{

		if(StringUtil.isBlank(path)){
			path+=parentid;
		}else
			path+=","+parentid;
		//System.out.println(path);
		ResultSet rs = stmt.executeQuery(LocationUtil.sql+parentid);
		/*ResultSetMetaData resultSetMD = rs.getMetaData();*/
	/*	for (int i = 1; i < resultSetMD.getColumnCount(); i++) {
			System.out.println("ColumnName:" + resultSetMD.getColumnName(i) + " " +
					"ColumnTypeName:" +
					resultSetMD.getColumnTypeName(i));
			System.out.println("isReadOnly:" + resultSetMD.isReadOnly(i)
					+ "  isWriteable:" + resultSetMD.isWritable(i)
					+ "  isNullable:" + resultSetMD.isNullable(i));
			System.out.println("tableName:" + resultSetMD.getTableName(i));
		}*/
		//get the id and the name
		//StringBuffer  jsonStr=new StringBuffer("'"+path+"':{");

		HashMap map =new HashMap();
		boolean hasData=false;
		while (rs.next()) {
			HashMap col = new HashMap();
			Long id = rs.getLong(1);
			String name = rs.getString(3);
			col.put("id", id);
			col.put("name", name);
			map.put(id,name);
			hasData=true;

		}
		if(hasData) {
			totalMap.put(path, map);
			rs.close();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Long key = (Long) it.next();
				String value = (String) map.get(key);
				getListByParentId(path, key, totalMap, stmt);
				//System.out.println(key + "→" + value);
			}
		}




	}
}
