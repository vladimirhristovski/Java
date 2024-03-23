package NP.NPKolokvium2.zad9;

import java.util.*;

class SeatTakenException extends Exception {

}

class SeatNotAllowedException extends Exception {

}


class Sector {
    String sectorName;
    int size;
    int type;
    List<Integer> seats;

    public Sector(String sectorName, int size) {
        this.sectorName = sectorName;
        this.size = size;
        this.seats = new ArrayList<>();
        this.type = 3;
    }

    public void addSeat(int seatNumber, int type) throws SeatTakenException, SeatNotAllowedException {
        if (seats.contains(seatNumber)) {
            throw new SeatTakenException();
        } else if (type != 0) {
            if (this.type == 3) {
                this.type = type;
            } else if (this.type != type) {
                throw new SeatNotAllowedException();
            }
        }
        seats.add(seatNumber);
    }

    public String getSectorName() {
        return sectorName;
    }

    public int getSize() {
        return size;
    }

    public double getPercent() {
        return (1.0 - ((double) getFreeSeats() / size)) * 100;
    }

    public int getFreeSeats() {
        return size - seats.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", sectorName, size - seats.size(), size, getPercent());
    }
}

class Stadium {
    String name;
    TreeSet<Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new TreeSet<>(Comparator.comparing(Sector::getFreeSeats).reversed().thenComparing(Sector::getSectorName));
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i = 0; i < sectorSizes.length; i++) {
            sectors.add(new Sector(sectorNames[i], sectorSizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
        for (Sector sector : sectors) {
            if (sector.getSectorName().equals(sectorName)) {
                sector.addSeat(seat, type);
            }
        }
    }

    public void showSectors() {
        sectors.stream()
                .sorted(Comparator.comparing(Sector::getFreeSeats).reversed().thenComparing(Sector::getSectorName))
                .forEach(System.out::println);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}