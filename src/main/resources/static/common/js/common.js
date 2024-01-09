var commonLib = commonLib || {};
commonLib.ajaxLoad = function(method, url, params, responseType){
    //ajax요청, 응답 편의 함수
    //method는 요청방식, url-요청, params는 요청 데이터(post,put,patch),
    //responseType은 json, javascript객체로 변환
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject)=> {
        const xhr = new XMLHttpRequest();

        xhr.open(method,url);
        xhr.setRequestHeader(tokenHeader,token);

        xhr.send(params);//요청 바디에 실릴 데이터 키=값&키=값&....formData객체에
        //post,put,patch방식
        xhr.onreadystatechange = function(){
            if(xhr.status ==200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resData = (responseType && responseType.toLowerCase()) ? JSON.parse(xhr.responseText) : xhr.responseText;
                resolve(resData);//성공시 응답 데이터
            }
        };
        xhr.onabort = function(err){
            reject(err);//중단 시
        };
        xhr.onerror = function(err){
            reject(err);//요청 또는 응답 시 오류 발생
        };
    });
};
/*
위지윅 에디터 로드
*/
commonLib.loadEditor = function(id, height){
    if(!id){
        return;
    }
    height=height || 450;

    //ClassicEditor
    return ClassicEditor.create(document.getElementById(id), {
        height
    });
}