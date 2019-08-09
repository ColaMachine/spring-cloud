package com.dozenx.common.util;

/**
 * 公共工具类
 * @author awifi-core
 * @date 2015年1月7日 下午2:59:36
 */
public class PermissionUtil {

	/**
	 * 判断是否有权限
	 * @param perReg
	 * @param permission
     * @return
     */
	public static boolean hasPermission(String perReg,String[] permission) {
		for(int i=0;i<permission.length;i++){
			if(permission[i].equals(perReg)){
				return true;
			}
		}
		return false;

		/*int start =0;
		boolean orFlag=false;
		boolean condition=false;



		for(int i =0;i<perReg.length();i++){
			HashSet set =new HashSet();

			RelationAcc relationAcc =new RelationAcc();
			char ch= perReg.charAt(i);
			if(ch=='('){

			}
			boolean kuohao=false;
			boolean find =false;
			switch (ch){
				case '(':
					kuohao=true;
					start=i;
					i=parseHexStringFromBytes(perReg,)
					continue;
				case ')':
					return;
					break;
				case '|':
					if(perReg.charAt(i+1)=='|'){
						condition=true;
					}
				default:
					continue;



			}

			if(find){
				set.add(new RelationAcc(perReg.substring(start,i)));
			}else
			if(condition){
				set.add(new RelationAcc(perReg.substring(start,i+1)));
				i++;
			}else{

			}
		}*/

	}
	public void readToken(String str){

	}


}
