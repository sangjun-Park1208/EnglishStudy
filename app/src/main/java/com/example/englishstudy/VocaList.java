package com.example.englishstudy;

import com.example.englishstudy.global.DBHelper;
import com.example.englishstudy.global.WordItem;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class VocaList {//넣을 단어들
    private int num=10;//단어 총개수
    private String[] word=new String[num];
    private String[] meaning=new String[num];
    private String wordtoken="apprehensive.on the payroll.effect.come into effect.take effect.put into effect(=apply).drastically.authorize" +
            "unauthorized production.enforce.enforce regulations.habit.convention.accustomed.corporate.corporation.compartment.upon request." +
            "manage.manage to do.be reminded to do.instruct.sample.well-deserved advancement.press release.extension.delegate.attentively.acquaint" +
            "file a claim.lax";
    private String meaingtoken="걱정하는.고용되어.결과로서 ~을 가져오다.실시되다.(법의)효력이 나타나다.법이 실행되어 현실에 적용.심하게.권한을 부여하다.불법 복제." +
            "(법률을)시행·집행하다.규정을 시행하다.(개개인의)습관.관례.익숙한.법인의.법인의.구획.요청 시에.~을 경영하다.가까스로 ~하다.~할 것을 잊지 않도록 주의받다.지시하다." +
            "(견본으로)시험하다.응당한 승진.보도 자료.연장.(권한 등을)위임하다.주의깊게.~에게 숙지시키다.(보험금 등을)청구하다.(행동 등이)느슨한";

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
