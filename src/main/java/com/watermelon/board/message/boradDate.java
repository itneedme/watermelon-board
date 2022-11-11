package com.watermelon.board.message;

class boradDate {
    long userVersion;
    long sheetId;
    String userId;

    public boradDate(long userVersion, long sheetId, String userId) {
        this.userVersion = userVersion;
        this.sheetId = sheetId;
        this.userId = userId;
    }

    public long getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(long userVersion) {
        this.userVersion = userVersion;
    }

    public long getSheetId() {
        return sheetId;
    }

    public void setSheetId(long sheetId) {
        this.sheetId = sheetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
