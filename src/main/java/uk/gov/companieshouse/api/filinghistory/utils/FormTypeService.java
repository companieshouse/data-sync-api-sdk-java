package uk.gov.companieshouse.api.filinghistory.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FormTypeService {


    private static List<String> blockList;

    private static final Pattern RESOLUTION_PATTERN = Pattern.compile(
            "^(?:E|(?:\\(W\\))*EL|S|L|W|O|)(?!<AU|CN)RES(?!T)");

    private FormTypeService() {}

    protected static List<String> getBlockList() {
        if (blockList == null) {
            initialiseBlockList();
        }
        return blockList;
    }

    public static boolean isAssociatedFilingBlockListed(TransactionKindCriteria criteria) {
        return getBlockList().contains(criteria.getParentFormType()) || getBlockList().contains(criteria.getFormType());
    }

    public static boolean isResolutionType(String formType) {
        return StringUtils.isNotBlank(formType) && RESOLUTION_PATTERN.matcher(formType).find();
    }

    private static void initialiseBlockList() {
        List<String> list = new ArrayList<String>();
        list.add("1(Scot)");
        list.add("1(scot)");
        list.add("1(SC)");
        list.add("1.1");
        list.add("1.1(NI)");
        list.add("1.1(Scot)");
        list.add("1.1(SC)");
        list.add("1.11");
        list.add("1.11(Scot)");
        list.add("1.11(SC)");
        list.add("1.12");
        list.add("1.12(Scot)");
        list.add("1.12(SC)");
        list.add("1.14");
        list.add("1.14(Scot)");
        list.add("1.14(SC)");
        list.add("1.2");
        list.add("1.3");
        list.add("1.3(NI)");
        list.add("1.3(Scot)");
        list.add("1.3(SC)");
        list.add("1.4");
        list.add("1.4(NI)");
        list.add("1.4(Scot)");
        list.add("1.4(SC)");
        list.add("12.1");
        list.add("2(Scot)");
        list.add("2(scot)");
        list.add("2.11B(Scot)");
        list.add("2.11B(SC)");
        list.add("2.12B");
        list.add("2.12B(NI)");
        list.add("2.15B(Scot)");
        list.add("2.15B(SC)");
        list.add("2.16B");
        list.add("2.16B(NI)");
        list.add("2.16B(Scot)");
        list.add("2.16B(SC)");
        list.add("2.16BZ(Scot)");
        list.add("2.16BZ(SC)");
        list.add("2.17B");
        list.add("2.17B(NI)");
        list.add("2.17B(Scot)");
        list.add("2.17B(SC)");
        list.add("2.18B");
        list.add("2.18B(NI)");
        list.add("2.18B(Scot)");
        list.add("2.18B(SC)");
        list.add("2.18BA(NI)");
        list.add("2.19");
        list.add("2.19B(Scot)");
        list.add("2.19B(SC)");
        list.add("2.20B(Scot)");
        list.add("2.20B(SC)");
        list.add("2.21B(Scot)");
        list.add("2.21B(SC)");
        list.add("2.22B");
        list.add("2.22B(NI)");
        list.add("2.22B(Scot)");
        list.add("2.22B(SC)");
        list.add("2.23B");
        list.add("2.23B(NI)");
        list.add("2.23B(Scot)");
        list.add("2.23B(SC)");
        list.add("2.24B");
        list.add("2.24B(NI)");
        list.add("2.24B(Scot)");
        list.add("2.24B(SC)");
        list.add("2.25B(Scot)");
        list.add("2.25B(SC)");
        list.add("2.26B");
        list.add("2.26B(NI)");
        list.add("2.27B");
        list.add("2.28B");
        list.add("2.2B(Scot)");
        list.add("2.2B(SC)");
        list.add("2.30B");
        list.add("2.30B(NI)");
        list.add("2.31B");
        list.add("2.31B(NI)");
        list.add("2.31B(Scot)");
        list.add("2.31B(SC)");
        list.add("2.32B");
        list.add("2.32B(NI)");
        list.add("2.32B(Scot)");
        list.add("2.32B(SC)");
        list.add("2.33B");
        list.add("2.33B(NI)");
        list.add("2.34B");
        list.add("2.34B(NI)");
        list.add("2.40B");
        list.add("2.40B(NI)");
        list.add("2.7");
        list.add("3.05(NI)");
        list.add("3.07(NI)");
        list.add("3.08(NI)");
        list.add("3.12(NI)");
        list.add("3.3");
        list.add("3.4");
        list.add("3.5(Scot)");
        list.add("3.5(scot)");
        list.add("3.5(SC)");
        list.add("3.6");
        list.add("39C(NI)");
        list.add("4.10");
        list.add("4.13");
        list.add("4.15");
        list.add("4.2(Scot)");
        list.add("4.2(SC)");
        list.add("4.20");
        list.add("4.21(NI)");
        list.add("4.27(Scot)");
        list.add("4.27(SC)");
        list.add("4.31");
        list.add("4.31(Scot)");
        list.add("4.31(SC)");
        list.add("4.32(NI)");
        list.add("4.44(NI)");
        list.add("4.48");
        list.add("4.48(NI)");
        list.add("4.49(NI)");
        list.add("4.51");
        list.add("4.68");
        list.add("4.69");
        list.add("4.69(NI)");
        list.add("4.70");
        list.add("4.71(NI)");
        list.add("4.9(Scot)");
        list.add("4.9(SC)");
        list.add("49(8)a");
        list.add("49(8)b");
        list.add("405(1)");
        list.add("558(NI)");
        list.add("600");
        list.add("703P(5)");
        list.add("ADD IN ADMIN");
        list.add("AM01");
        list.add("AM01(Scot)");
        list.add("AM02");
        list.add("AM02(Scot)");
        list.add("AM03");
        list.add("AM03(Scot)");
        list.add("AM04");
        list.add("AM05");
        list.add("AM06");
        list.add("AM06(Scot)");
        list.add("AM07");
        list.add("AM07(Scot)");
        list.add("AM08");
        list.add("AM09");
        list.add("AM10");
        list.add("AM11");
        list.add("AM12");
        list.add("AM19");
        list.add("AM20");
        list.add("AM21");
        list.add("AM22");
        list.add("AM25");
        list.add("CERT8A");
        list.add("CO4.2(Scot)");
        list.add("CO4.2(SC)");
        list.add("COCOMP");
        list.add("COM1");
        list.add("COM2");
        list.add("COM3");
        list.add("COM4");
        list.add("CVA1");
        list.add("CVA3");
        list.add("CVA4");
        list.add("CVA1(Scot)");
        list.add("CVA2(Scot)");
        list.add("CVA3(Scot)");
        list.add("CVA4(Scot)");
        list.add("DETERMINAT");
        list.add("DETERMINAT(NI)");
        list.add("DETERM(NI)");
        list.add("EC REGS F");
        list.add("EC_REGS_F");
        list.add("F10.2");
        list.add("F14");
        list.add("F2.18");
        list.add("F9.4");
        list.add("L22(NI)");
        list.add("LIQ MISC");
        list.add("LIQ MISC OC");
        list.add("LIQ MISC OC(NI)");
        list.add("LIQ MISC OC(NI)");
        list.add("LIQ MISC RES");
        list.add("LIQ MISC(NI)");
        list.add("LIQ MISCRES(NI)");
        list.add("LIQMISC");
        list.add("LIQMISCNIO");
        list.add("LIQMISCRES");
        list.add("LIQMISC(NI)");
        list.add("LIQMISCRES(NI)");
        list.add("LIQ01");
        list.add("LIQ02");
        list.add("LIQ03");
        list.add("LLNM01");
        list.add("LLRM01");
        list.add("LQ01");
        list.add("LRES(NI)");
        list.add("LRESC(NI)");
        list.add("LRESEX");
        list.add("LRESM(NI)");
        list.add("LRESSP");
        list.add("NDISC");
        list.add("NOCP");
        list.add("O/C STAY");
        list.add("O/C_STAY");
        list.add("O/C UNSTAY");
        list.add("O/C_UNSTAY");
        list.add("OCRESCIND");
        list.add("OSDS02");
        list.add("OSLQ01");
        list.add("OSLQ02");
        list.add("OSLQ03");
        list.add("OSLQ04");
        list.add("REC2");
        list.add("RM01");
        list.add("VAM7");
        list.add("VL1");
        list.add("WU04");
        list.add("WU07");
        list.add("CERT10");
        list.add("CERT3");
        list.add("MAR");
        list.add("RES02");
        list.add("RES06");
        list.add("49(1)");
        list.add("49(8)(a)");
        list.add("49(8)(b)");
        list.add("53");
        list.add("(W)ELRES");
        list.add("ELRES");
        list.add("ERES01");
        list.add("ERES03");
        list.add("ERES04");
        list.add("ERES06");
        list.add("ERES07");
        list.add("ERES08");
        list.add("ERES09");
        list.add("ERES10");
        list.add("ERES11");
        list.add("ERES12");
        list.add("ERES13");
        list.add("ERES14");
        list.add("ORES01");
        list.add("ORES03");
        list.add("ORES04");
        list.add("ORES05");
        list.add("ORES06");
        list.add("ORES07");
        list.add("ORES08");
        list.add("ORES09");
        list.add("ORES10");
        list.add("ORES11");
        list.add("ORES12");
        list.add("ORES13");
        list.add("ORES14");
        list.add("RES01");
        list.add("RES03");
        list.add("RES 03");
        list.add("RES04");
        list.add("RES05");
        list.add("RES07");
        list.add("RES08");
        list.add("RES09");
        list.add("RES10");
        list.add("RES 10");
        list.add("RES11");
        list.add("RES12");
        list.add("RES13");
        list.add("RES 13");
        list.add("RES14");
        list.add("RES16");
        list.add("SRES01");
        list.add("SRES02");
        list.add("SRES03");
        list.add("SRES04");
        list.add("SRES05");
        list.add("SRES06");
        list.add("SRES07");
        list.add("SRES08");
        list.add("SRES09");
        list.add("SRES10");
        list.add("SRES11");
        list.add("SRES12");
        list.add("SRES13");
        list.add("SRES14");
        list.add("SRES16");
        list.add("WRES01");
        list.add("WRES02");
        list.add("WRES03");
        list.add("WRES04");
        list.add("WRES05");
        list.add("WRES06");
        list.add("WRES07");
        list.add("WRES08");
        list.add("WRES09");
        list.add("WRES10");
        list.add("WRES11");
        list.add("WRES12");
        list.add("WRES13");
        list.add("WRES14");
        list.add("RR01");
        list.add("RR02");
        list.add("RR04");
        list.add("RR05");
        list.add("RR06");
        list.add("RR07");
        list.add("RR09");
        list.add("RR10");
        list.add("AUDR");
        list.add("AUDS");
        list.add("BS");
        list.add("CERT1");
        list.add("CERT11");
        list.add("CERT2");
        list.add("CERT23");
        list.add("CERT24");
        list.add("CERT4");
        list.add("CERT5");
        list.add("CERT7");
        list.add("FOA-RR");
        list.add("RRFOA");
        list.add("RROC138");
        list.add("RROC649");
        list.add("VAL");
        list.add("RP04SH01");
        list.add("RP04SH02");
        list.add("RP04SH05");
        list.add("RP04SH06");
        list.add("RP04SH07");
        list.add("RP04SH14");
        list.add("RP04SH15");
        list.add("MT01");
        list.add("MT02");
        list.add("MT03");
        list.add("MT04");
        list.add("MT05");
        list.add("MT06");
        list.add("MT07");
        list.add("MT08");
        list.add("MT09");
        blockList = Collections.unmodifiableList(list);
    }
}
