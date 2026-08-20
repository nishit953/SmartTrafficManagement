import dao.AdminDAO;
import dao.JunctionDAO;
import dao.VehicleLogDAO;
import dao.ViolationDAO;
import model.SimpleVehicle;
import model.Vehicle;
import model.ViolationRecord;
import model.TrafficViolation;
import service.TrafficSignal;
import utils.Console;
import utils.ReportGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.*;

public class SmartTrafficManagement {
    private static void printMenu() {
        System.out.println(" ================= Smart Traffic Menu =================");
        System.out.println(" 1. Add Vehicle");
        System.out.println(" 2. Start Traffic Processing (threaded)");
        System.out.println(" 3. Search Vehicle");
        System.out.println(" 4. Delete Vehicle");
        System.out.println(" 5. Log a Violation");
        System.out.println(" 6. Show Violation & Log Stats");
        System.out.println(" 7. Generate Text Report");
        System.out.println(" 8. Export Violations CSV");
        System.out.println(" 9. Configure Signal Durations");
        System.out.println("10. Show Violations for a Vehicle");
        System.out.println("11. Exit");
        System.out.println("======================================================");
    }

    //for regular series number plates.
    public static boolean RegularSeriesPlate(String plate)
    {
        // Regex pattern for Indian vehicle number plates
        String regex = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{1,4}$";

        // Compile regex
        Pattern pattern = Pattern.compile(regex);

        // Match against input
        Matcher matcher = pattern.matcher(plate);

        return matcher.matches();
    }

    //for bharat series plates.
    public static boolean BharatSeriesPlate(String plate)
    {
        // Format: 2 digits + 2 letters + 4 digits + 1 letter
        String regex = "^[0-9]{2}[A-Z]{2}[0-9]{4}[A-Z]$";
        return Pattern.matches(regex, plate);
    }

    //for temporary series number plates.
    public static boolean TemporarySeriesPlate(String plate)
    {
        // Regex for format: 1 letter + 4 digits + 2 letters + 4 digits + 1 letter
        String regex = "^[A-Z][0-9]{4}[A-Z]{2}[0-9]{4}[A-Z]$";
        return Pattern.matches(regex, plate);
    }

    private static void printViolationsMenu()
    {
        System.out.println("Select a violation type:");
        System.out.println(" " + TrafficViolation.RED_LIGHT_JUMP + ". " + TrafficViolation.getViolationName(TrafficViolation.RED_LIGHT_JUMP));
        System.out.println(" " + TrafficViolation.WRONG_SIDE + ". " + TrafficViolation.getViolationName(TrafficViolation.WRONG_SIDE));
        System.out.println(" " + TrafficViolation.NO_HELMET_OR_SEATBELT + ". " + TrafficViolation.getViolationName(TrafficViolation.NO_HELMET_OR_SEATBELT));
        System.out.println(" " + TrafficViolation.OVERSPEEDING + ". " + TrafficViolation.getViolationName(TrafficViolation.OVERSPEEDING));
        System.out.println(" " + TrafficViolation.DRINK_AND_DRIVE + ". " + TrafficViolation.getViolationName(TrafficViolation.DRINK_AND_DRIVE));
        System.out.println(" " + TrafficViolation.RECKLESS_DRIVING + ". " + TrafficViolation.getViolationName(TrafficViolation.RECKLESS_DRIVING));
        System.out.println(" " + TrafficViolation.MOBILE_USE + ". " + TrafficViolation.getViolationName(TrafficViolation.MOBILE_USE));
        System.out.println(" " + TrafficViolation.NO_VALID_LICENSE + ". " + TrafficViolation.getViolationName(TrafficViolation.NO_VALID_LICENSE));
        System.out.println(" " + TrafficViolation.NO_INSURANCE + ". " + TrafficViolation.getViolationName(TrafficViolation.NO_INSURANCE));
        System.out.println(" " + TrafficViolation.NO_WAY_FOR_EMERGENCY + ". " + TrafficViolation.getViolationName(TrafficViolation.NO_WAY_FOR_EMERGENCY));
        System.out.println(" " + TrafficViolation.OVERLOADING_PASSENGERS + ". " + TrafficViolation.getViolationName(TrafficViolation.OVERLOADING_PASSENGERS));
        System.out.println(" " + TrafficViolation.NO_VALID_PUC + ". " + TrafficViolation.getViolationName(TrafficViolation.NO_VALID_PUC));
    }

    public static void main(String[] args) {
        Console io = new Console();

        // --- DB setup ---
        AdminDAO adminDAO = new AdminDAO();
        JunctionDAO junctionDAO = new JunctionDAO();
        VehicleLogDAO vlogs = new VehicleLogDAO();
        ViolationDAO vdao = new ViolationDAO();

        // --- Admin login ---
        System.out.println("=== Admin Login ===");
        String adminUser;
        while (true) {
            String u = io.readNonEmpty("Username: ");
            String p = io.readNonEmpty("Password: ");
            if (adminDAO.login(u, p)) {
                adminUser = u;
                System.out.println("[OK] Logged in as " + u);
                break;
            }
            System.out.println("Invalid credentials. Try again.");
        }

        // --- Junction select or create ---
        System.out.println("=== Select or Create Junction ===");
        String jName = io.readNonEmpty("Junction name: ");
        String jLoc = io.readNonEmpty("Junction location: ");
        int junctionId = junctionDAO.ensureJunction(jName, jLoc);
        System.out.println("Using junction #" + junctionId + " (" + jName + ")");

        TrafficSignal signal = new TrafficSignal(vdao, vlogs, junctionId, adminUser);

        while (true) {
            printMenu();
            int choice = io.readInt("Choose an option: ", 1, 11);
            switch (choice) {
                case 1: {
                    System.out.println("Enter the type of the number plate series:- ");
                    System.out.println(" -1. Regular series.");
                    System.out.println(" -2. Bharat series.");
                    System.out.println(" -3. Temporary series.");
                    int c = io.readInt("Choose an option: ", 1, 3);
                    String num = io.readNonEmpty("Enter vehicle number: ");
                    boolean emerg = io.readBoolean("Is it an emergency vehicle? (true/false): ");
                    boolean m=true;
                    while(m)
                    {
                        boolean checkPlate;
                        if(c==1)
                        {
                            checkPlate=RegularSeriesPlate(num);
                            if(checkPlate)
                            {
                                System.out.println(num + " → " + checkPlate);
                                signal.addVehicle(new SimpleVehicle(num, emerg));
                                System.out.println("[OK] Vehicle added.");
                                m=false;
                            }
                            else
                            {
                                System.out.println(num + " → " + checkPlate);
                                System.out.println("Entered Number Plate is Invalid.");
                                System.out.println("Correct format is 'AA00AA0000' OR '00AA0000A' OR 'A0000AA0000A'(Here 'A' represents aplhabet and '0' represents digit). ");
                                System.out.println("Please try again!!");
                            }
                        }
                        if(c==2)
                        {
                            checkPlate=BharatSeriesPlate(num);
                            if(checkPlate)
                            {
                                System.out.println(num + " → " + checkPlate);
                                signal.addVehicle(new SimpleVehicle(num, emerg));
                                System.out.println("[OK] Vehicle added.");
                                m=false;
                            }
                            else
                            {
                                System.out.println(num + " → " + checkPlate);
                                System.out.println("Entered Number Plate is Invalid.");
                                System.out.println("Correct format is 'AA00AA0000' OR '00AA0000A' OR 'A0000AA0000A'(Here 'A' represents aplhabet and '0' represents digit). ");
                                System.out.println("Please try again!!");
                            }
                        }
                        if(c==3)
                        {
                            checkPlate=TemporarySeriesPlate(num);
                            if(checkPlate)
                            {
                                System.out.println(num + " → " + checkPlate);
                                signal.addVehicle(new SimpleVehicle(num, emerg));
                                System.out.println("[OK] Vehicle added.");
                                m=false;
                            }
                            else
                            {
                                System.out.println(num + " → " + checkPlate);
                                System.out.println("Entered Number Plate is Invalid.");
                                System.out.println("Correct format is 'AA00AA0000' OR '00AA0000A' OR 'A0000AA0000A'(Here 'A' represents aplhabet and '0' represents digit). ");
                                System.out.println("Please try again!!");
                            }
                        }
                    }

                    break;
                }
                case 2: {
                    Thread t = new Thread(signal);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException ignored) {
                    }
                    break;
                }
                case 3: {
                    String num = io.readNonEmpty("Enter vehicle number to search: ");
                    System.out.println(signal.searchVehicle(num) ? "Vehicle found." : "Vehicle not found.");
                    break;
                }
                case 4: {
                    String num = io.readNonEmpty("Enter vehicle number to delete: ");
                    String sql = "DELETE FROM violations WHERE vehicleNumber = ?";
                    final String URL = "jdbc:mysql://localhost:3306/trafficdb";
                    final String USER = "root";
                    final String PASS = "";
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                         PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, num);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("Error deleting violations: " + e.getMessage());
                    }

                    System.out.println("Deleted if existed.");
                    break;
                }
                case 5: {
                    String num = io.readNonEmpty("Enter vehicle number: ");
                    printViolationsMenu();
                    int code = io.readInt("Enter number (1-12): ", 1, 12);
                    signal.logViolation(num, code);
                    break;
                }
                case 6: {
                    int total = vdao.getViolationCount();
                    int[] counts = vdao.getCountsByType();
                    System.out.println(" === Violation Stats ===");
                    System.out.println("Total violations: " + total);
                    for (int i = 1; i < counts.length; i++)
                        System.out.println("- " + TrafficViolation.getViolationName(i) + " " + counts[i]);
                    String[][] top = vdao.getTopOffenders(10);
                    if (top.length > 0) {
                        System.out.println("Top Offenders:");
                        for (int i = 0; i < top.length; i++)
                            System.out.println((i + 1) + " " + top[i][0] + " " + top[i][1] + " violations");
                    } else
                        System.out.println("No offenders yet.");
                    System.out.println(" === Vehicle Log Stats ===");
                    String[][] ac = vlogs.countsByAction();
                    for (int i = 0; i < ac.length; i++)
                        System.out.println("- " + ac[i][0] + ": " + ac[i][1]);
                    break;
                }
                case 7: {
                    Vehicle[] all = signal.getAllVehicles();
                    int count = all.length;
                    String name = "traffic_report_" + System.currentTimeMillis() + ".txt";
                    ReportGenerator.generateTextReport(name, all, count, vdao, vlogs);
                    break;
                }
                case 8: {
                    String name = "violation_summary_" + System.currentTimeMillis() + ".csv";
                    ReportGenerator.exportViolationsCsv(name, vdao);
                    break;
                }
                case 9: {
                    long r = io.readInt("Red ms (>=100): ", 100, 10000);
                    long y = io.readInt("Yellow ms (>=100): ", 100, 10000);
                    long g = io.readInt("Green ms (>=100): ", 100, 10000);
                    signal.configureDurations(r, y, g);
                    System.out.println("Durations updated.");
                    break;
                }
                case 10: {
                    String num = io.readNonEmpty("Enter vehicle number: ");
                    ViolationRecord[] recs = vdao.getViolationsForVehicle(num.toUpperCase());
                    if (recs.length == 0) {
                        System.out.println("No violations for this vehicle.");
                        break;
                    }
                    System.out.println("Violations for " + num + ":");
                    for (int i = 0; i < recs.length; i++) {
                        ViolationRecord r = recs[i];
                        System.out.println((i + 1) + " " + TrafficViolation.getViolationName(r.typeCode) + " " + r.time + (r.junctionId == null ? "" : " @Junction#" + r.junctionId) + (r.adminUser == null ? "" : " by " + r.adminUser));
                    }
                    break;
                }
                case 11: {
                    System.out.println("Exiting the system...............");
                    System.out.println("================================");
                    return;
                }
                default:
                    System.out.println("Invalid input, Please try again.");
            }
        }
    }
}