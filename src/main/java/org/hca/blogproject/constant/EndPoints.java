package org.hca.blogproject.constant;

public class EndPoints {
    public static final String VERSION="/v7";
    //profiller:
    public static final String API="/api";
    public static final String DEV="/dev";
    public static final String TEST="/test";

    public static final String ROOT=API+VERSION;

    //entities:
    public static final String POST="/posts";
    public static final String CATEGORY="/categories";
    public static final String USER="/users";
    public static final String COMMENT="/comments";

    //methods:
    public static final String CREATE="";
    public static final String GET_ALL="";
    public static final String UPDATE="/{id}";
    public static final String DELETE="/{id}";
    public static final String FIND_BY_ID="/{id}";
    public static final String FIND_BY_NAME ="/categoryName";
    public static final String SET_TO_DELETED ="/setToDeleted/{id}";
    public static final String GET_POSTS_BY_USER_ID ="user/{userId}";
    public static final String REACTIVATE_USER ="/reActivateUser/{id}";
    public static final String GET_POSTS_BY_CATEGORY_ID ="/category/{categoryId}";
    public static final String GET_POSTS_BY_CATEGORY_NAME ="/searchByCategoryName";
    public static final String SEARCH ="/searchByKeyword";
    public static final String LIKE ="/like";
    public static final String UNLIKE ="/unlike";
    public static final String GET_POSTS_IN_CHRONOLOGICAL_ORDER ="/sortPostsByPublication";
}
