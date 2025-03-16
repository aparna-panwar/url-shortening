package com.dkb.url.shortening.utility;

import org.unbrokendome.base62.Base62;

public class UrlShortenUtility {

    public static String urlEncryption(String url){
        return Base62.encode(url.hashCode());
    }
}
