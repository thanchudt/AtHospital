package com.gmail.dangle1107.vn.athospital;

import java.util.Comparator;

public class MarkComparator implements Comparator<KnowledgeDto>
{
    public int compare(KnowledgeDto left, KnowledgeDto right) {
    	int ret = 0;
        if (left.order < right.order)
        	ret = -1;
        else if (left.order > right.order)
    		ret = 1;
        return ret;
    }
}
