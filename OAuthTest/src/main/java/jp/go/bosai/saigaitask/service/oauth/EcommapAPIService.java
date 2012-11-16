package jp.go.bosai.saigaitask.service.oauth;

/**
 * eコミマップのAPIのURLを生成するクラスです.
 * 基本的には以下のメソッドがあります.
 * POST: 新規作成
 * PUT: 情報更新
 * DELETE: 削除
 * GET: 単体情報取得
 * GETS: 一覧情報取得
 */
public class EcommapAPIService {

	/** eコミマップAPIバージョン */
	public static final String API_VERSION = "2.0";

	/** APIを利用するeコミマップのサーバー.(例: "http://XXXXX/map") */
	public String ecomServer;

	//====================================================================
	// WhoAmI API
	//====================================================================
	/**
	 * OAuth認証でログイン中のユーザ情報を取得します.
	 * @return url
	 */
	public String getWhoAmI() {
		String url = "/api/user/me";
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Version API
	//====================================================================
	/**
	 * eコミマップのAPIバージョンを取得します.
	 * @return url
	 */
	public String getVersion() {
		String url = "/api/version";
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// User API
	//====================================================================
	public String postUser(int cid) {
		String url = "/api/user/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putUser(int cid, int user) {
		String url = "/api/user/"+cid+"/"+user;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteUser(int cid, int user) {
		String url = "/api/user/"+cid+"/"+user;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getUser(int cid, int user) {
		String url = "/api/user/"+cid+"/"+user;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsUser(int cid) {
		String url = "/api/user/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String joinUser(int cid, int user) {
		String url = "/api/user/"+cid+"/"+user;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Group API
	//====================================================================
	public String postGroup(int cid) {
		String url = "/api/group/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putGroup(int cid, int group) {
		String url = "/api/group/"+cid+"/"+group;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteGroup(int cid, int group) {
		String url = "/api/group/"+cid+"/"+group;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getGroup(int cid, int group) {
		String url = "/api/group/"+cid+"/"+group;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsGroup(int cid) {
		String url = "/api/group/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Map API
	//====================================================================
	public String postMap(int cid) {
		String url = "/api/map/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putMap(int cid, int map) {
		String url = "/api/map/"+cid+"/"+map;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteMap(int cid, int map) {
		String url = "/api/map/"+cid+"/"+map;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getMap(int cid, int map) {
		String url = "/api/map/"+cid+"/"+map;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsMap(int cid) {
		String url = "/api/map/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Layer API
	//====================================================================
	public String postLayer(int cid) {
		String url = "/api/layer/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putLayer(int cid, int layer) {
		String url = "/api/layer/"+cid+"/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteLayer(int cid, int layer) {
		String url = "/api/layer/"+cid+"/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getLayer(int cid, int layer) {
		String url = "/api/layer/"+cid+"/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsLayer(int cid) {
		String url = "/api/layer/"+cid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// MapLayer API
	//====================================================================
	/**
	 * 地図に既存のレイヤーを追加します.
	 * @param cid サイトID
	 * @param map 地図ID
	 * @param layer レイヤID
	 * @return url
	 */
	public String postMapLayer(int cid, int map, String layer) {
		String url = "/api/map/"+cid+"/"+map+"/layer/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putMapLayer(int cid, int map, String layer) {
		String url = "/api/map/"+cid+"/"+map+"/layer/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteMapLayer(int cid, int map, String layer) {
		String url = "/api/map/"+cid+"/"+map+"/layer/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getMapLayer(int cid, int map, String layer) {
		String url = "/api/map/"+cid+"/"+map+"/layer/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsMapLayer(int cid, int map) {
		String url = "/api/map/"+cid+"/"+map+"/layer";
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Attr API
	//====================================================================
	public String postAttr(int cid, int layer, String attr) {
		String url = "/api/layer/"+cid+"/"+layer+"/attr";
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putAttr(int cid, int layer, String attr) {
		String url = "/api/layer/"+cid+"/"+layer+"/attr/"+attr;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteAttr(int cid, int layer, String attr) {
		String url = "/api/layer/"+cid+"/"+layer+"/attr/"+attr;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getAttr(int cid, int layer, String attr) {
		String url = "/api/layer/"+cid+"/"+layer+"/attr/"+attr;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsAttr(int cid, int layer) {
		String url = "/api/layer/"+cid+"/"+layer+"/attr";
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Feature API
	//====================================================================
	public String postFeature(int cid, int mid, int layer) {
		String url = "/api/feature/"+cid+"/"+mid+"/"+layer;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putFeature(int cid, int mid, int layer, String feature) {
		String url = "/api/feature/"+cid+"/"+mid+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteFeature(int cid, int mid, int layer, String feature) {
		String url = "/api/feature/"+cid+"/"+mid+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getFeature(int cid, int mid, int layer, String feature) {
		String url = "/api/feature/"+cid+"/"+mid+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsFeature(int cid, int mid) {
		String url = "/api/feature/"+cid+"/"+mid;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// Comment API
	//====================================================================
	public String postComment(int cid, int layer, String feature) {
		String url = "/api/comment/"+cid+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String putComment(int cid, int layer, String feature, int comment) {
		String url = "/api/comment/"+cid+"/"+layer+"/"+feature+"/"+comment;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteComment(int cid, int layer, String feature, int comment) {
		String url = "/api/comment/"+cid+"/"+layer+"/"+feature+"/"+comment;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getComment(int cid, int layer, String feature) {
		String url = "/api/comment/"+cid+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// File API
	//====================================================================
	public String postFile(int cid, int map, int layer, String feature) {
		String url = "/api/file/"+cid+"/"+map+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String deleteFile(int cid, int map, int layer, String feature, int file) {
		String url = "/api/file/"+cid+"/"+map+"/"+layer+"/"+feature+"/"+file;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	public String getsFile(int cid, int map, int layer, String feature) {
		String url = "/api/file/"+cid+"/"+map+"/"+layer+"/"+feature;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

	//====================================================================
	// WMS API
	//====================================================================
	public String getWMS(int cid, int map) {
		String url = "/api/wms/"+cid+"/"+map;
		if(ecomServer!=null) {
			return ecomServer + url;
		}
		return url;
	}

}
