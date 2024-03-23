package NP.NPKolokvium1.zad8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Archive{
    int getId();
    String getType();
    LocalDate getDateToOpen();
    int getMaxOpen();
    void setOpened(int n);
    int getOpened();
}

class NonExistingItemException extends Exception{
    public NonExistingItemException(String message) {
        super(message);
    }
}

class LockedArchive implements Archive{
    int id;
    LocalDate dateToOpen;
    String type;

    public LockedArchive(int id, LocalDate dateToOpen) {
        this.id = id;
        this.dateToOpen = dateToOpen;
        this.type="LOCKED";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public LocalDate getDateToOpen() {
        return dateToOpen;
    }

    @Override
    public int getMaxOpen() {
        return 0;
    }

    @Override
    public void setOpened(int n) {
        //DO NOTHING
    }

    @Override
    public int getOpened() {
        return 0;
    }

    @Override
    public String getType() {
        return type;
    }
}

class SpecialArchive implements Archive{
    int id;
    int maxOpen;
    int opened;

    String type;

    public SpecialArchive(int id, int maxOpen) {
        this.id = id;
        this.maxOpen = maxOpen;
        this.type="SPECIAL";
        this.opened=0;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public LocalDate getDateToOpen() {
        return null;
    }
    @Override
    public void setOpened(int opened) {
        this.opened = opened;
    }
    @Override
    public int getOpened() {
        return opened;
    }
}
class ArchiveStore{
    List<Archive>archives;
    List<String>log;

    public ArchiveStore() {
        this.archives=new ArrayList<>();
        this.log=new ArrayList<>();
    }
    void archived(int id, LocalDate date){
        //Item 3 archived at 2013-10-07
        log.add(String.format("Item %d archived at %s\n",id,date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
    void archiveItem(Archive item, LocalDate date){
        if(item.getType().equals("LOCKED")){
            archives.add(new LockedArchive(item.getId(),item.getDateToOpen()));
            archived(item.getId(),date);
        }
        if(item.getType().equals("SPECIAL")){
            archives.add(new SpecialArchive(item.getId(),item.getMaxOpen()));
            archived(item.getId(),date);
        }
    }

    boolean searchElement(int id){
        for (Archive archive : archives) {
            if(archive.getId()==id){
                return false;
            }
        }
        return true;
    }
    void opened(int id, LocalDate date){
        //Item 3 archived at 2013-10-07
        log.add(String.format("Item %d opened at %s\n",id,date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
    void locked(int id, LocalDate date){
        //Item 3 archived at 2013-10-07
        log.add(String.format("Item %d cannot be opened before %s\n",id,date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }
    void special(int id, int maxTimes){
        //Item 3 archived at 2013-10-07
        log.add(String.format("Item %d cannot be opened more than %d times\n",id,maxTimes));
    }
    void openItem(int id, LocalDate date) throws NonExistingItemException {
        if(searchElement(id)){
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist",id));
        }
        for (Archive archive : archives) {
            if(archive.getId()==id) {
                if (archive.getType().equals("LOCKED")) {
                    if (archive.getDateToOpen().isAfter(date)) {
                        locked(archive.getId(), archive.getDateToOpen());
                    }else {
                        opened(archive.getId(),date);
                    }
                }

                if (archive.getType().equals("SPECIAL")) {
                    if (archive.getOpened()==archive.getMaxOpen()) {
                        special(archive.getId(), archive.getMaxOpen());
                    }else {
                        archive.setOpened(archive.getOpened()+1);
                        opened(archive.getId(),date);
                    }
                }
            }
        }
    }
    String getLog(){
        StringBuilder sb=new StringBuilder();
        for (String s : log) {
            sb.append(s);
        }
        return sb.toString();
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}