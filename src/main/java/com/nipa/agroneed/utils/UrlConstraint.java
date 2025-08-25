package com.nipa.agroneed.utils;

public final class UrlConstraint {
    public UrlConstraint() {
    }
    public static class Categories {
        public static final String ROOT = "/Categories";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
        public static final String GET_ALL_PARENT_CATEGORY = "/getAllParentCategory";
        public static final String GET_BY_ID = "/getById/{id}";
        public static final String GET_BY_ParentId = "/getByParentId/{parentId}";
        public static final String GET_PRODUCTS_AND_CATEGORY_BY_CATEGORY_ID = "/getProductsAndCategoryByCategoryId/{categoryId}";
        public static final String DELETE_BY_ID = "/delete/{id}";
        public static final String EDIT_BY_ID = "/edit/{id}";

    }
    public static class Products{
        public static final String ROOT = "/Products";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";

    }
    public static class Users{
        public static final String ROOT = "/users";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
    }
    public static class ShoppingCart{
        public static final String ROOT = "/shoppingCart";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
        public static final String INCREMENT_DECREMENT = "/incrementDecrement";
        public static final String REMOVE_ROW="/removeRow";
        //public static final String REMOVE_ROW="/removeRow/{productId}";
    }
    public static class Orders {
        public static final String ROOT = "/orders";
        public static final String PLACE_ORDER = "/placeOrder";
        public static final String GET_ALL = "/getAll";
        public static final String UPADATE_ORDER = "/ordersUpdate";
    }

    public static class Suppliers {
        public static final String ROOT = "/suppliers";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
    }


    }
