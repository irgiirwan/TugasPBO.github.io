package CRUD1;

import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database db = new Database();
        String key;
        int index;
        String pilihan;
        boolean status;
        System.out.println("APLIKASI SIMPLE CRUD TEXT DATABASE");
        while (true) {
            db.menuPilihan();
            System.out.print("Pilih: ");
            pilihan = sc.nextLine().toUpperCase();
            switch (pilihan) {
                case "C":
                    System.out.println("Anda memasukki mode Create");
                    System.out.println("----------------------------------------------------");
                    System.out.println("INPUT DATA BARU");
                    System.out.print("Masukkan NIM            :");
                    String nim = sc.nextLine();
                    System.out.print("Masukkan Nama Mahasiswa :");
                    String nama = sc.nextLine();
                    System.out.print("Masukkan Alamat         :");
                    String alamat = sc.nextLine();
                    System.out.print("Masukkan Semester       :");
                    int semester = sc.nextInt();
                    System.out.print("Masukkan SKS            :");
                    int sks = sc.nextInt();
                    System.out.print("Masukkan IPK            :");
                    double ipk = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("----------------------------------------------------");
                    status = db.insert(nim,nama,alamat,semester,sks,ipk);
                    if(status == true) {
                        System.out.println("Data Berhasil Ditambahkan!");
                    } else {
                        System.err.println("NIM: "+nim+" sudah ada di Database");
                        System.err.printf("Gagal menambahkan data");
                    }
                    break;
                case "R":
                    System.out.println("Anda memasukki mode Read");
                    db.read();
                    break;
                case "U":
                    System.out.println("Anda memasukki mode Update");
                    db.read();
                    System.out.print("Masukkan NIM yang ingin diubah: ");
                    key = sc.nextLine();
                    index = db.search(key);
                    if(index >= 0) {
                        System.out.println("Anda akan mengubah data dari "+db.getData().get(index));
                        System.out.println("----------------------------------------------------");
                        System.out.println("INPUT DATA BARU");
                        System.out.print("Masukkan NIM            :");
                        nim = sc.nextLine();
                        System.out.print("Masukkan Nama Mahasiswa :");
                        nama = sc.nextLine();
                        System.out.print("Masukkan Alamat         :");
                        alamat = sc.nextLine();
                        System.out.print("Masukkan Semester       :");
                        semester = sc.nextInt();
                        System.out.print("Masukkan SKS            :");
                        sks = sc.nextInt();
                        System.out.print("Masukkan IPK            :");
                        ipk = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("----------------------------------------------------");
                        System.out.print("Apakah anda yakin ingin mengubah data tersebut? Y/N: ");
                        pilihan = sc.nextLine();
                        if(pilihan.equalsIgnoreCase("Y")) {
                            status = db.update(index,nim,nama,alamat,semester,sks,ipk);
                            if(status == true) {
                                System.out.println("Data berhasil diubah!");
                            } else {
                                System.err.printf("Gagal mengubah data");
                            }
                        } else {
                            System.out.println("Proses pengubahan data dibatalkan.");
                        }
                    } else {
                        System.err.println("Mahasiswa dengan NIM: "+key+" tidak ada di Database");
                    }
                    break;
                case "D":
                    System.out.println("Anda memasukki mode Delete");
                    db.read();
                    System.out.print("Masukkan NIM yang ingin dihapus: ");
                    key = sc.nextLine();
                    index = db.search(key);
                    if(index >= 0) {
                        System.out.println("Apakah anda yakin menghapus data "+db.getData().get(index)+"? Y/N");
                        pilihan = sc.nextLine();
                        if(pilihan.equalsIgnoreCase("Y")) {
                            status = db.delete(index);
                            if(status == true) {
                                System.out.println("Data Berhasil Dihapus");
                            } else {
                                System.out.println("Data Gagal Dihapus");
                            }
                        } else {
                            System.out.println("Proses Penghapusan Dibatalkan");
                        }
                    } else {
                        System.err.println("Mahasiswa dengan NIM: "+key+" tidak ada di Database");
                    }
                    break;
                case "X":
                    System.out.println("Anda memasukki mode Exit");
                    System.out.println("Apakah anda yakin ingin keluar? Y/N");
                    System.out.print("Pilih: ");
                    pilihan = sc.nextLine();
                    if(pilihan.equalsIgnoreCase("Y")) {
                        System.exit(0);
                    } else {
                        System.out.println("Proses keluar dibatalkan");
                    }
                    break;
                default:
                    System.out.println("Input Tidal Valid. Silahkan Input Ulang.");
                    break;
            }
        }
    }
}
