package bestPackage.twPaperErrors;

public class Device {
    private String vehicleNumber;
    private String depotNumber;

    String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    String getDepotNumber() {
        return depotNumber;
    }

    void setDepotNumber(String depotNumber) {
        this.depotNumber = depotNumber;
    }

    Device(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
