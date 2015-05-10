package com.example.triptap;

public class Const 
{
	public static final String URL_JSON_OBJECT = "http://api.androidhive.info/volley/person_object.json";
	public static final String URL_JSON_ARRAY = "http://api.androidhive.info/volley/person_array.json";
	public static final String URL_STRING_REQ = "http://api.androidhive.info/volley/string_response.html";
	public static final String URL_IMAGE = "http://api.androidhive.info/volley/volley-image.jpg";
	public static final String LNW_JSON = "http://www.case-d.com/json/showroom/recommend";
	
	// Mongolab API and 4square API
	public static final String apiKey = "pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
	public static final String CLIENT_ID = "VQFA1NFZFVHNCSQL1GTBVAOWBDQOHSQEHOW5YZKU1IS1JRFO";
	public static final String CLIENT_SECRET = "KMIYI5FXHQFHCQYKRE35EKX125UEH4AQERSJRXMAZXDRFLDF";
	
	// Trip Me
	public static final String CAT_TRIPME = "https://api.mongolab.com/api/1/databases/triptap_location_category/collections/category";
	public static final String RULE_TRIPME = "https://api.mongolab.com/api/1/databases/triptap_tripme_rules/collections/rules";
	public static final String VENUE_TRIPME = "https://api.mongolab.com/api/1/databases/triptap_venue_information/collections/venues";
	
	// Trip For You
	public static final String BEHAVIOR = "https://api.mongolab.com/api/1/databases/triptap_user_data/collections/user_data?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
	
	public static final String CATT = "https://api.mongolab.com/api/1/databases/triptap_location_category/collections/category" +
			"?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N" +
			"&q=%7B++%22state_init%22+%3A+%22Lampang%22+++%7D";
	
	public static final String RULE = "https://api.mongolab.com/api/1/databases/triptap_tripme_rules/collections/rules" +
			"?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N" +
			"&q=%7B%22state_init%22%3A%22Phuket%22%2C%22%24or%22%3A%5B%7B%22catsPremiss.catName%22%3A%7B%24all%3A%5B%22Beach%22%2C%22Mountain%22%5D%7D%7D%2C%7B%22catsConclu.catName%22%3A%7B%24all%3A%5B%22Beach%22%2C%22Mountain%22%5D%7D%7D%5D%7D";

	public static final String VENUE = "https://api.foursquare.com/v2/venues/"+ "3829995"+"?&client_id="+ CLIENT_ID +"&client_secret="+ CLIENT_SECRET +"&v=20130815";
	
	public static final String FS = "https://api.foursquare.com/v2/venues/4da5570f0cb6d75e29f5fb13?&client_id=VQFA1NFZFVHNCSQL1GTBVAOWBDQOHSQEHOW5YZKU1IS1JRFO&client_secret=KMIYI5FXHQFHCQYKRE35EKX125UEH4AQERSJRXMAZXDRFLDF&v=20130815";
	
	public static final String RULEF = "https://api.mongolab.com/api/1/databases/triptap_tripme_rules/collections/rules?q={%22state_init%22:%22" +
			"Phuket" +
			"%22,%22catAll.catName%22:{$all:[%22" +
			"Beach" +
			"%22,%22" +
			"Bridge" +
			"%22,%22" +
			"Buddhist%20Temple" +
			"%22]}}&apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
	
	public static final String RULED = "https://api.mongolab.com/api/1/databases/triptap_tripme_rules/collections/rules?q={%22state_init%22:%22" +
			"Phuket" +
			"%22,%22catAll.catName%22:{$all:[%22" +
			"Beach" +
			"%22,%22" +
			"Mountain" +
			"%22]}}&apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
}

