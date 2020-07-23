package com.example.jjoo_argentinian_athletes.util;

import java.util.Locale;

public class Const {
    public static final String API_ATHLETES_URL = "http://imartinez-oh56jopdsgcsvtla3p.s3.amazonaws.com/data_athletes_".concat(Locale.getDefault().getLanguage()).concat(".json");
    public static final String API_SPORTS_URL = "http://imartinez-oh56jopdsgcsvtla3p.s3.amazonaws.com/data_sports_".concat(Locale.getDefault().getLanguage().concat(".json"));
}
