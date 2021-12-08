package com.example.englishstudy;

import java.util.StringTokenizer;

public class VocaList {//넣을 단어들
    private int num=90;//단어 총개수
    private String[] word=new String[num];
    private String[] meaning=new String[num];
    private String wordtoken=
            "receive.standby.container.banister.guide book.hot rod.clear off the table.leaflet.truck.on the floor."+
            "carefree.look up.in progress.ball.grocery.lay down the law.bathe.recliner.supply.cross legged."+
            "press the button.mend.lathe.go for a ride.run out of.wedding party.cut corners.tan.cubicle.defense."+
            "start.street vendor.facsimile.row.display.baggage.sack.gamble.record.pass."+
            "waterfall.blimp.recycler.build.line up.get with.toward.pointer.guard.nail down."+
            "for sale.strain to.stack up.crew.baked.speeding ticket.walk about.commuter.frame of mind.hoop."+
            "chandelier.graze.stroll.trip.magazine staff.rake.stand up.sightseer.second floor.subway stop."+
            "riverside.bank branch.dressing room.map.incident.museum.cord.outdoor.section.Popsicle."+
            "escalate.pose.trim.copious.dig.on display.teeth.couple.practicing.take off.";
    private String meaingtoken=
            "받다,받아들이다.비상용품,대기자.그릇,컨테이너.난간.안내서.개조한 자동차.식탁을 깨끗이 치우다.전단지,인쇄물.트럭.무일푼의,빈털터리의."+
            "근심 걱정없는.올려다보다,찾아보다.진행 중인.공,무도회.식료품 잡화점.야단치다.씻다,세척하다.안락의자.공급,공급하다.아빠다리를하고."+
            "버튼을 누르다.수리하다,고치다.선반.타러 가다.~을 다 써버리다.결혼식 파티,피로연.절약하다,지름길로 가다.햇볕에 타다.작은 방,탈의실.방어."+
            "시작하다,시작.노점상.복제,복사.노를 젓다,열.전시하다,전시.짐,수하물.파면,해고.돈을걸다,도박.기록,등록.지나가다,통과하다."+
            "폭포.소형 비행선.재생 처리기.짓다,건설하다.줄을 서다.~에 주목하다.~을 향하여,~쪽으로.충고,신호.경비요원,보초.고정시키다,확실하게 하다."+
            "팔려고 내놓은.~에 안간힘을 쓰다.계속 쌓이다,비교할 만하다.승무원.취한,햇볕에 탄.속도위반 딱지.돌아다니다.통근자.마음의 상태,기분.퉁근 테,링."+
            "샹들리에.방목하다,풀을 뜯다.거닐다,산책하다.여행,이동.잡지부 직원.갈퀴,난봉꾼.서 있다.관광객,유람객.2층.지하철역."+
            "강가,강변.은행지점.분장실,탈의실.지도.일,사건.박물관.줄,끈.옥외의,집밖에.구역,부서.아이스캔디."+
            "확대하다,악화되다.제기하다,포즈를 취하다.다듬다,장식.엄청난,방대한.땅을 파다.전시된.이,이빨.두사람,연결하다.활동하고있는.이륙하다,떠나다.";

    private StringTokenizer tword=new StringTokenizer(wordtoken,".");
    private StringTokenizer tmeaning=new StringTokenizer(meaingtoken,".");

    public VocaList() {
        for(int i=0;i<num;i++){
            word[i]=tword.nextToken();
            meaning[i]=tmeaning.nextToken();
        }
    }

    public String getWord(int index) {
        return word[index];
    }

    public String getMeaning(int index) {
        return meaning[index];
    }
}
