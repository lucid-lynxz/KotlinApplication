package lynxz.org.kotlinapplication.bean

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 * u+go登录前获取的token实体类
 *
 * {
"access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVC8J.eyJzY29wZSI6WyJvcGVuaWQiLCJ3cml0ZSIsInJlYWQiLCJzb3BfYmFzZSIsInNvcF91c2hvcCIsInNvcF9zdW5iYXIiLCJzb3BfdWFjIiwic29wX21lcmNoYW50Iiwic29wX3B1c2giXSwiZXhwIjoxNDgzMDIxMTUwLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzczYTdmNjQtZWE4OC00NWM1LWIzNzAtMjUxZTg4NjA2MzJmIiwiY2xpZW50X2lkIjoidXBsdXNnbyJ9.hH_6vemVSfeZQ5rltoqAJ0Sz9BHLfjINeNY5ZWtGTHmoqEx6iBoWfkO1N0mrVDQgifAvA7SIIy6MwxwkqbwHyVHV1POpYHq_GsiY3CwW0dXozN-MJJ2BTqrkRgOiG21zSJifCsF6ID5a4f_IBZKUecme1M99skg-heakB-HGgGfmurpKdIsvBNNO3ydZcpweXRC0LiNZOsmxvl2nGe7O5jd98N9RZ59rQim3ptj1SQzkmUuFkU6KECzs8-DminCjUUb-Iy1__G5evnsEV85J9fCcOecqdIVUhI3ul2Q_T4aiAl8C9WCmnhho6qhT3Oxd64LT-Bf6Uu4iJBn4kUWNSw",
"token_type": "bearer",
"expires_in": 22864,
"scope": "openid write read sop_base sop_ushop sop_sunbar sop_uac sop_merchant sop_push",
"jti": "773a7f64-ea88-45c5-b370-251e8860642e"
}
 */
class UPlusGoGetTokenBean(var access_token: String? = null,
                          var token_type: String? = null,
                          var expires_in: Long = 0,
                          var scope: String? = null,
                          var jti: String? = null)

