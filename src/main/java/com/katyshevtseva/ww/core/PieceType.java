package com.katyshevtseva.ww.core;

import com.katyshevtseva.ww.core.enums.PieceSubtype;

import java.util.List;

public interface PieceType {

    String getTitle();

    List<PieceSubtype> getSubtypes();
}
