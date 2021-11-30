package com.example.englishstudy.Test;

import com.example.englishstudy.global.WordItem;

import java.util.Comparator;

public class WordItemIdComparator implements Comparator<WordItem> {

    @Override
        public int compare(WordItem o1, WordItem o2) {
            if(o1.getId() > o2.getId()){
                return 1;
            }
            else if(o1.getId() < o2.getId()){
                return -1;
            }
            else{
                return 0;
            }

        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }

}
