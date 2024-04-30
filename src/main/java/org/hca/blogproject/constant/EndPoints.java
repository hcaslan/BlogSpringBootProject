package org.hca.blogproject.constant;

public class EndPoints {
    public static final String VERSION="/v1";
    //profiller:
    public static final String API="/api";
    public static final String DEV="/dev";
    public static final String TEST="/test";

    public static final String ROOT=API+VERSION;

    //entities:
    public static final String POST="/posts";
    public static final String CATEGORY="/categories";
    public static final String USER="/users";

    //methods:
    public static final String UPDATE="/{id}";
    public static final String DELETE="/{id}";
    public static final String FIND_BY_ID="/{id}";
    public static final String SET_TO_DELETED ="/settodeleted/{id}";
    public static final String GET_POSTS_BY_USER_ID ="user/{id}";
    public static final String GET_POSTS_BY_CATEGORY_ID ="/category/{id}";
    public static final String GET_POSTS_BY_CATEGORY_NAME ="/category";
    public static final String SEARCH ="/search";
}
