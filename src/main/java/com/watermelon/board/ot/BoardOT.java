package com.watermelon.board.ot;

import java.util.List;

public interface BoardOT {

    // A的M操作和B的N操作合并
    List<? extends Comparable> twoOT(List<?> lA, List<?> lB);

}
