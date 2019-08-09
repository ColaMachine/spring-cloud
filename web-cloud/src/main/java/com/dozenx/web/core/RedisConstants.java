package com.dozenx.web.core;

/**
 * Created by dozen.zhang on 2016/9/30.
 */
public class RedisConstants {
    /**地区key前缀*/
    public static final String LOCATION = "advert_loction_";

    public static final String LOCATION_CHILDS = "advert_loction_childs_";

    public static final String LOCATION_IDNAME = "advert_loction_idname_";
    /**地区key有效时间*/
    public static final int LOCATION_TIME = 2678400;//31*24*60*60(31天)

    public static final String REDIS_DBC_ACCESS_TOKEN ="advert_dbc_access_token";
    /**行业key前缀*/
    public static final String INDUSTRY = "advert_industry_";
    /**行业key有效时间*/
    public static final int INDUSTRY_TIME = 2678400;//31*24*60*60(31天)
    /**一天的秒数*/
    public static final int DAY_SECONDS=24*60*60;

    public static final int HOUR_SECONDS=60*60;

    public static final String TOKEN_REDIS_KEY="token_redis_key";
}
