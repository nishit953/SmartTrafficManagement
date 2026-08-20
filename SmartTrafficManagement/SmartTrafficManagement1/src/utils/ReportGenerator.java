package utils;

import dao.VehicleLogDAO;
import dao.ViolationDAO;
import model.Vehicle;
import model.TrafficViolation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportGenerator {
    public static void generateTextReport(String filename, Vehicle[] vehicles, int count, ViolationDAO vdao, VehicleLogDAO ldao) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (Writer w = new FileWriter(filename)) {
            w.write("Traffic Report - Generated at: " + LocalDateTime.now().format(fmt) + "\n");
            w.write("Processed Vehicles (order):\n");
            for (int i = 0; i < count && i < vehicles.length; i++) {
                Vehicle v = vehicles[i];
                if (v == null)
                    break;
                w.write(String.format("%4d) %-12s %s\n", i + 1, v.getVehicleNumber(), v.isEmergency() ? "Emergency" : "Regular"));
            }
            w.write("\nViolation Summary (from DB):\n");
            int total = vdao.getViolationCount();
            w.write("Total violations: " + total + "\n");
            int[] counts = vdao.getCountsByType();
            for (int i = 1; i < counts.length; i++) {
                w.write(String.format("- %-35s : %d\n", TrafficViolation.getViolationName(i), counts[i]));
            }
            w.write("\nTop Offenders:\n");
            String[][] top = vdao.getTopOffenders(10);
            for (int i = 0; i < top.length; i++) {
                w.write(String.format("%02d) %-12s  %s violations\n", i + 1, top[i][0], top[i][1]));
            }
            w.write("\nVehicle Log Summary:\n");
            String[][] ac = ldao.countsByAction();
            for (int i = 0; i < ac.length; i++) {
                w.write(String.format("- %-10s : %s\n", ac[i][0], ac[i][1]));
            }
            w.write("\n-- End of Report --\n");
            System.out.println("[REPORT] Text report written to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportViolationsCsv(String filename, ViolationDAO vdao) {
        try (Writer w = new FileWriter(filename)) {
            w.write("typeCode,typeLabel,count\n");
            int[] counts = vdao.getCountsByType();
            for (int i = 1; i < counts.length; i++) {
                String label = TrafficViolation.getViolationName(i).replace("\"", "'");
                w.write(i + ",\"" + label + "\"," + counts[i] + "\n");
            }
            System.out.println("[REPORT] CSV summary written to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}