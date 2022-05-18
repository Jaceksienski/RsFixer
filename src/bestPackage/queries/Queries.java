package bestPackage.queries;

import bestPackage.GlobalData;

public class Queries {
    private GlobalData globalData;

    public Queries(GlobalData globalData) {
        this.globalData = globalData;
    }

    public String queryTW1() {
        return "select * from" +
                "(SELECT datetime, device_id, status,row_number() over ( partition by device_id order by datetime desc) lp  FROM status_logs_state a JOIN status_logs_printer b " +
                "ON a.status_logs_printer_id = b.status_logs_printer_id WHERE " +
                " datetime BETWEEN '" + globalData.getDateMinus30Minutes() + "' AND '" + globalData.getDateMinus10Minutes() + "') aa where lp=1 AND status =5 ";
    }

    public String queryTW2(String s) {
        return "SELECT is_active FROM device_config WHERE device_id = " + s;
    }

    public String queryTW3(String s) {
        return "SELECT tvm_groups.name FROM tvm_groups JOIN tvms " +
                "ON tvms.tvm_group_id = tvm_groups.id " +
                "WHERE " +
                "tvms.name = 'Automat " + s + "'";
    }

    public String queryDBloadBydgoszcz() {
        return "SELECT api_address, device_id FROM public.device_config WHERE " +
                "is_active = TRUE AND " +
                "is_decommissioned IS NOT TRUE AND " +
                "is_hidden IS NOT TRUE AND " +
                "cast(device_id as varchar(6)) LIKE '___1' OR " +
                "cast(device_id as varchar(6)) LIKE '___9' " +
                "order by device_id";
    }

    public String queryDBloadGdansk() {
        return "SELECT api_address, device_id FROM public.device_config WHERE " +
                "is_active = TRUE AND " +
                "is_decommissioned  IS NOT TRUE AND " +
                "is_hidden IS NOT TRUE AND " +
                "cast(device_id as varchar(7)) LIKE '____1' " +
                "order by device_id ";
    }

    public String queryDBloadLodz() {
        return "SELECT api_address, device_id FROM public.device_config WHERE " +
                "is_active = TRUE AND " +
                "is_decommissioned  IS NOT TRUE AND " +
                "is_hidden IS NOT TRUE AND " +
                "cast(device_id as varchar(7)) LIKE '____1' " +
                "order by device_id";
    }

    public String queryDBloadJaworzno() {
        return "SELECT api_address, device_id FROM public.device_config WHERE " +
                "is_active = TRUE AND " +
                "is_decommissioned  IS NOT TRUE AND " +
                "is_hidden IS NOT TRUE AND " +
                "cast(device_id as varchar(7)) LIKE '1___' " +
                "order by device_id";
    }
}

