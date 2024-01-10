package org.choongang.commons;

public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;
    public Pagination(int page, int total, int ranges, int limit){
        //생성자-현재페이지, 전체 레코드 개수, 한페이지 구간에 몇개, 한 페이지당 레코드 개수
        page = Utils.onlyPositiveNumber(page,1);
        total = Utils.onlyPositiveNumber(total,0);
        ranges = Utils.onlyPositiveNumber(ranges,10);
        limit =Utils.onlyPositiveNumber(limit,20);

        int totalPages =(int)Math.ceil(total /(double) limit);
        //구간번호-0,1,2..
        int rangeCnt = (page-1) / ranges;
        int firstRangePage = rangeCnt * ranges+1;
        int lastRangePage = firstRangePage+ranges-1;

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
    }
}
