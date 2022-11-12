package com.watermelon.board.service.ot;

public interface IOT {

//    // A的M操作和B的N操作合并
//    List<? extends Comparable> twoOT(List<?> lA, List<?> lB);

    /**
     * 通用OT。如果版本不符合应该的版本，就退回
     * @return
     */
    boolean generalOT(abstractOperate opt);
}
