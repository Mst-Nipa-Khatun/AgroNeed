package com.nipa.agroneed.enums;

public enum SupplierOrderStatus {

    RECEIVED_ORDER(1, "Received Order"),
    SHIFTED(6, "Shifted"),
    DELIVERED(7, "Delivered"),
    SHIFTED_TO_PACKING(8, "Shifted to Packing");

    private final int id;
    private final String displayName;

    SupplierOrderStatus(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String getNameById(int id) {
        for (SupplierOrderStatus status : values()) {
            if (status.getId() == id) {
                return status.getDisplayName();
            }
        }
        return "Unknown Status"; // fallback if not found
    }

    public static SupplierOrderStatus getById(int id) {
        for (SupplierOrderStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }
}
