package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Data
public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;

    private int firstRangePage;
    private int lastRangePage;
    private int prevRangePage;//이전구간의 첫 페이지 번호
    private int nextRangePage;
    private int totalPages;//전체 페이지 개수
    private String baseURL;

    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request){
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
        lastRangePage = lastRangePage > totalPages ? totalPages : lastRangePage;

        //이전구간 첫 페이지
        if(rangeCnt > 0){
            prevRangePage = firstRangePage - ranges;
        }
        //다음구간의 첫페이지-마지막구간에서는 구하지 않는다
        //마지막구간의 번호
        int lastRangeCnt = (totalPages -1)/ranges;
        if(rangeCnt < lastRangeCnt){
            //현재구간 번호가 마지막이 아닐때
            nextRangePage = firstRangePage +ranges;
            //페이지번호와 구간번호는 다름

        }
        //쿼리스트링값-유지(page만 제외하고 검색데이터다시조합)
        //?page=2 ---> ?로만 유지
        String baseURL = "?";
        if(request!=null){
            String queryString = request.getQueryString();

            if(StringUtils.hasText(queryString)){
                queryString = queryString.replace("?","");
                baseURL += Arrays.stream(queryString.split("&"))
                        .filter(s-> !s.contains("page="))
                        .collect(Collectors.joining("&"));
            baseURL = baseURL.length() > 1 ? baseURL += "&" :baseURL;
            }


        }
        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.totalPages=totalPages;
       this.baseURL=baseURL;
    }
    public Pagination(int page,int total, int ranges, int limit){
        this(page,total,ranges,limit,null);
    }

    //string배열로-mapToObj
    public List<String[]> getPages(){
        //0-페이지번호, 1-페이지 url-?page=페이지번호
        return IntStream.rangeClosed(firstRangePage,lastRangePage)
                .mapToObj(p-> new String[]{ String.valueOf(p),
                        baseURL + "?page="+p})
                .toList();
    }
}
