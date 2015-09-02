
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sleepy
 */
public class Absensi extends javax.swing.JFrame {

    private int countSiswa, countKelas, countAbsen;
    private final boolean[] JenisKelamin = new boolean[9999];
    private final int[] KodeKelas = new int[9999], KodeKelasSiswa = new int[9999], NIS = new int[9999], NISAbsen = new int[9999], KelasAbsen = new int[9999];
    private final String[] NamaKelas = new String[9999], NamaSiswa = new String[9999], Jam1 = new String[9999],
            Jam2 = new String[9999], Jam3 = new String[9999], Jam4 = new String[9999];
    private final java.sql.Date[] Tanggal = new java.sql.Date[9999];
    private DefaultComboBoxModel VarKelas, VarNIS;
    private JComboBox comboBox = new JComboBox();
    private DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{
                "NIS", "Nama", "Jenis Kelamin", "Kelas"
            }
    ) {
        Class[] types = new Class[]{
            java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class
        };
        boolean[] canEdit = new boolean[]{
            false, false, false, false
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };
    private DefaultTableModel model2 = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{
                "NIS", "Nama", "Jenis Kelamin", "Jam 1", "Jam 2", "Jam 3", "Jam 4"
            }
    ) {
        Class[] types = new Class[]{
            java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class, java.lang.String.class, java.lang.String.class,
            java.lang.String.class
        };
        boolean[] canEdit = new boolean[]{
            false, false, false, true, true, true, true
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };

    /**
     * Creates new form Absensi
     */
    public Absensi() {
        initComponents();
        comboBox.addItem("Hadir");
        comboBox.addItem("Alpa");
        comboBox.addItem("Izin");
        comboBox.addItem("Sakit");
        loadData();
        detailBtn.setEnabled(false);
        panelCount.setVisible(false);
    }

    public Absensi(int kodeKelas) {
        loadData2();
        loadDataSiswa2(kodeKelas);
    }

    private void loadData() {
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            try (Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM `kelas` ")) {
                int i = 0;
                while (r.next()) {
                    //lakukan penelusuran baris
                    KodeKelas[i] = r.getInt("ID");
                    NamaKelas[i] = r.getString("Nama");
                    i++;
                    countKelas = i;
                }
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Variabel Unit) Load data error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {
            String list[] = new String[countKelas];
            for (int i = 0; i < countKelas; i++) {
                list[i] = NamaKelas[i];
            }
            VarKelas = new javax.swing.DefaultComboBoxModel(list);
            listKelas.setModel(VarKelas);
        }
    }

    private void loadData2() {
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            try (Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM `kelas` ")) {
                int i = 0;
                while (r.next()) {
                    //lakukan penelusuran baris
                    KodeKelas[i] = r.getInt("ID");
                    NamaKelas[i] = r.getString("Nama");
                    i++;
                    countKelas = i;
                }
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Variabel Unit) Load data error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private void loadDataSiswa(int idKelas) {
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            try (Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM `siswa` WHERE `kelas` = " + idKelas)) {
                int i = 0;
                while (r.next()) {
                    //lakukan penelusuran baris
                    NIS[i] = r.getInt("NIS");
                    NamaSiswa[i] = r.getString("Nama");
                    JenisKelamin[i] = r.getBoolean("Jenis Kelamin");
                    KodeKelasSiswa[i] = r.getInt("Kelas");
                    i++;
                    countSiswa = i;
                }
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Variabel Unit) Load data error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {
            String list[] = new String[countSiswa];
            for (int i = 0; i < countSiswa; i++) {
                list[i] = String.valueOf(NIS[i]);
            }
            VarNIS = new javax.swing.DefaultComboBoxModel(list);
            loadTableSiswa();
        }
    }

    private void loadDataSiswa2(int idKelas) {
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            try (Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM `siswa` WHERE `kelas` = " + idKelas)) {
                int i = 0;
                while (r.next()) {
                    //lakukan penelusuran baris
                    NIS[i] = r.getInt("NIS");
                    NamaSiswa[i] = r.getString("Nama");
                    JenisKelamin[i] = r.getBoolean("Jenis Kelamin");
                    KodeKelasSiswa[i] = r.getInt("Kelas");
                    i++;
                    countSiswa = i;
                }
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Variabel Unit) Load data error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    public final void loadTableSiswa() {
        if (model.getDataVector() != null) {
            model.getDataVector().removeAllElements();
        }
        // memberi tahu bahwa data telah kosong
        model.fireTableDataChanged();
        try {
            for (int i = 0; i < countSiswa; i++) {
                Object[] o = new Object[4];
                o[0] = NIS[i];
                o[1] = NamaSiswa[i];
                if (JenisKelamin[i] == true) {
                    o[2] = "Laki-laki";
                } else {
                    o[2] = "Perempuan";
                }
                o[3] = getNamaKelas(KodeKelasSiswa[i]);
                model.addRow(o);
            }
        } finally {
            Tabel.setModel(model);
            detailBtn.setEnabled(true);
        }
    }

    private void loadAbsen(java.util.Date tanggal, int idKelas) {
        try {
            java.sql.Date sqlDate;
            if (tanggal != null) {
                sqlDate = new java.sql.Date(tanggal.getTime());
            } else {
                sqlDate = null;
            }
            Connection c = Class_KoneksiDatabase.getKoneksi();
            try (Statement s = c.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM `absensi` WHERE `Tanggal` = '" + sqlDate + "' AND `Kelas` = " + idKelas)) {
                int i = 0;
                while (r.next()) {
                    //lakukan penelusuran baris
                    Tanggal[i] = r.getDate("Tanggal");
                    NISAbsen[i] = r.getInt("NIS");
                    Jam1[i] = r.getString("Jam1");
                    Jam2[i] = r.getString("Jam2");
                    Jam3[i] = r.getString("Jam3");
                    Jam4[i] = r.getString("Jam4");
                    KelasAbsen[i] = r.getInt("Kelas");
                    i++;
                    countAbsen = i;
                }
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Variabel Unit) Load data error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {
            loadTableAbsen();
        }
    }

    private void addAbsen(Object[] o) {
        java.util.Date utilDate = (java.util.Date) o[0];
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            String sql = "INSERT INTO `absensi` (`Tanggal`, `NIS`, `Jam1`,"
                    + "`Jam2`, `Jam3`, `Jam4`, `Kelas`) "
                    + "VALUES (?, ?, NULL, NULL, NULL, NULL, ?)";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setDate(1, sqlDate);
                p.setInt(2, (int) o[1]);
                p.setInt(3, (int) o[2]);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Data Absen)Insert record error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {
            //loadAbsen(utilDate, (int) o[2]);
        }
    }

    private void updateAbsen1(Object[] o) {
        java.util.Date utilDate = (java.util.Date) o[2];
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            String sql = "UPDATE `absensi` "
                    + "SET `Jam1` = ? "
                    + "WHERE `NIS` = ? AND `Tanggal` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, (String) o[0]);
                p.setInt(2, (int) o[1]);
                p.setDate(3, sqlDate);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Data Absen1)Update record error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {

        }
    }

    private void updateAbsen2(Object[] o) {
        java.util.Date utilDate = (java.util.Date) o[2];
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            String sql = "UPDATE `absensi` "
                    + "SET `Jam2` = ? "
                    + "WHERE `NIS` = ? AND `Tanggal` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, (String) o[0]);
                p.setInt(2, (int) o[1]);
                p.setDate(3, sqlDate);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Data Absen2)Update record error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {

        }
    }

    private void updateAbsen3(Object[] o) {
        java.util.Date utilDate = (java.util.Date) o[2];
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            String sql = "UPDATE `absensi` "
                    + "SET `Jam3` = ? "
                    + "WHERE `NIS` = ? AND `Tanggal` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, (String) o[0]);
                p.setInt(2, (int) o[1]);
                p.setDate(3, sqlDate);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Data Absen3)Update record error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {

        }
    }

    private void updateAbsen4(Object[] o) {
        java.util.Date utilDate = (java.util.Date) o[2];
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        try {
            Connection c = Class_KoneksiDatabase.getKoneksi();
            String sql = "UPDATE `absensi` "
                    + "SET `Jam4` = ? "
                    + "WHERE `NIS` = ? AND `Tanggal` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, (String) o[0]);
                p.setInt(2, (int) o[1]);
                p.setDate(3, sqlDate);
                p.executeUpdate();
            }
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                default:
                    JOptionPane.showMessageDialog(null,
                            e.getErrorCode() + " : " + e.getMessage(),
                            "(Data Absen4)Update record error!",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } finally {

        }
    }

    public final void loadTableAbsen() {
        if (countAbsen != 0) {
            if (model2.getDataVector() != null) {
                model2.getDataVector().removeAllElements();
            }
            // memberi tahu bahwa data telah kosong
            model2.fireTableDataChanged();
            try {
                for (int i = 0; i < countAbsen; i++) {
                    Object[] o = new Object[7];
                    o[0] = NISAbsen[i];
                    o[1] = getNamaSiswa(NISAbsen[i]);
                    o[2] = getJenisKelamin(NISAbsen[i]);
                    if (Jam1[i] != null) {
                        switch (Jam1[i]) {
                            case "H":
                                o[3] = "Hadir";
                                break;
                            case "A":
                                o[3] = "Alpa";
                                break;
                            case "I":
                                o[3] = "Izin";
                                break;
                            case "S":
                                o[3] = "Sakit";
                                break;
                        }
                    } else {
                        o[3] = Jam1[i];
                    }
                    if (Jam2[i] != null) {
                        switch (Jam2[i]) {
                            case "H":
                                o[4] = "Hadir";
                                break;
                            case "A":
                                o[4] = "Alpa";
                                break;
                            case "I":
                                o[4] = "Izin";
                                break;
                            case "S":
                                o[4] = "Sakit";
                                break;
                        }
                    } else {
                        o[4] = Jam2[i];
                    }
                    if (Jam3[i] != null) {
                        switch (Jam3[i]) {
                            case "H":
                                o[5] = "Hadir";
                                break;
                            case "A":
                                o[5] = "Alpa";
                                break;
                            case "I":
                                o[5] = "Izin";
                                break;
                            case "S":
                                o[5] = "Sakit";
                                break;
                        }
                    } else {
                        o[5] = Jam3[i];
                    }
                    if (Jam4[i] != null) {
                        switch (Jam4[i]) {
                            case "H":
                                o[6] = "Hadir";
                                break;
                            case "A":
                                o[6] = "Alpa";
                                break;
                            case "I":
                                o[6] = "Izin";
                                break;
                            case "S":
                                o[6] = "Sakit";
                                break;
                        }
                    } else {
                        o[6] = Jam4[i];
                    }
                    model2.addRow(o);
                }
            } finally {
                Tabel.setModel(model2);
                TableColumn columnJam1 = Tabel.getColumnModel().getColumn(3);
                TableColumn columnJam2 = Tabel.getColumnModel().getColumn(4);
                TableColumn columnJam3 = Tabel.getColumnModel().getColumn(5);
                TableColumn columnJam4 = Tabel.getColumnModel().getColumn(6);
                columnJam1.setCellEditor(new DefaultCellEditor(comboBox));
                columnJam2.setCellEditor(new DefaultCellEditor(comboBox));
                columnJam3.setCellEditor(new DefaultCellEditor(comboBox));
                columnJam4.setCellEditor(new DefaultCellEditor(comboBox));
                ActionListener cbActionListener = new ActionListener() {//add actionlistner to listen for change
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int i = Tabel.getSelectedRow();
                        String s = (String) comboBox.getSelectedItem();//get the selected item
                        String s2 = (String) comboJam.getSelectedItem();
                        Object[] o = new Object[3];
                        if (s != null) {
                            switch (s) {//check for a match
                                case "Hadir":
                                    o[0] = "H";//jam
                                    o[1] = model2.getValueAt(i, 0);//nis
                                    o[2] = comboDate.getDate();//tanggal
                                    switch (s2) {
                                        case "1":
                                            updateAbsen1(o);
                                            break;
                                        case "2":
                                            updateAbsen2(o);
                                            break;
                                        case "3":
                                            updateAbsen3(o);
                                            break;
                                        case "4":
                                            updateAbsen4(o);
                                            break;
                                    }
                                    break;
                                case "Alpa":
                                    o[0] = "A";//jam
                                    o[1] = model2.getValueAt(i, 0);//nis
                                    o[2] = comboDate.getDate();//tanggal
                                    switch (s2) {
                                        case "1":
                                            updateAbsen1(o);
                                            break;
                                        case "2":
                                            updateAbsen2(o);
                                            break;
                                        case "3":
                                            updateAbsen3(o);
                                            break;
                                        case "4":
                                            updateAbsen4(o);
                                            break;
                                    }
                                    break;
                                case "Izin":
                                    o[0] = "I";//jam
                                    o[1] = model2.getValueAt(i, 0);//nis
                                    o[2] = comboDate.getDate();//tanggal
                                    switch (s2) {
                                        case "1":
                                            updateAbsen1(o);
                                            break;
                                        case "2":
                                            updateAbsen2(o);
                                            break;
                                        case "3":
                                            updateAbsen3(o);
                                            break;
                                        case "4":
                                            updateAbsen4(o);
                                            break;
                                    }
                                    break;
                                case "Sakit":
                                    o[0] = "S";//jam
                                    o[1] = model2.getValueAt(i, 0);//nis
                                    o[2] = comboDate.getDate();//tanggal
                                    switch (s2) {
                                        case "1":
                                            updateAbsen1(o);
                                            break;
                                        case "2":
                                            updateAbsen2(o);
                                            break;
                                        case "3":
                                            updateAbsen3(o);
                                            break;
                                        case "4":
                                            updateAbsen4(o);
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                };
                comboBox.addActionListener(cbActionListener);
                countAbsen = 0;
            }
        } else {
            if (model2.getDataVector() != null) {
                model2.getDataVector().removeAllElements();
            }
            // memberi tahu bahwa data telah kosong
            model2.fireTableDataChanged();
            try {
                for (int i = 0; i < countSiswa; i++) {
                    Object[] o = new Object[7];
                    o[0] = getNIS(KodeKelas[i]);
                    o[1] = getNamaSiswa(getNIS(KodeKelas[i]));
                    o[2] = getJenisKelamin(getNIS(KodeKelas[i]));
                    o[3] = "-";
                    o[4] = "-";
                    o[5] = "-";
                    o[6] = "-";
                    model2.addRow(o);
                }
            } finally {
                Object[] o = new Object[3];
                o[0] = comboDate.getDate();//tanggal
                String namaKelas = listKelas.getSelectedValue().toString();
                for (int i = 0; i < NamaKelas.length; i++) {
                    if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                        o[2] = KodeKelas[i];
                    }
                }
                for (int i = 0; i < model2.getRowCount(); i++) {//kolom NIS
                    o[1] = model2.getValueAt(i, 0);//baris, kolom
                    addAbsen(o);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listKelas = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        comboDate = new org.jdesktop.swingx.JXDatePicker();
        absenToggle = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        comboJam = new javax.swing.JComboBox();
        panelCount = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        labelHadir = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelAlpa = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelIzin = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelSakit = new javax.swing.JLabel();
        labelTgl = new javax.swing.JLabel();
        detailBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Abensi");

        listKelas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listKelasMouseClicked(evt);
            }
        });
        listKelas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listKelasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listKelas);

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(Tabel);

        comboDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateActionPerformed(evt);
            }
        });

        absenToggle.setText("Absen");
        absenToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                absenToggleActionPerformed(evt);
            }
        });

        jLabel1.setText("Jam ke - :");

        comboJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        comboJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJamActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Jumlah Siswa/i, Hadir :");

        labelHadir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelHadir.setText("99");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText(", Alpa :");

        labelAlpa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAlpa.setText("99");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText(", Izin :");

        labelIzin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelIzin.setText("99");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText(", Sakit :");

        labelSakit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelSakit.setText("99");

        javax.swing.GroupLayout panelCountLayout = new javax.swing.GroupLayout(panelCount);
        panelCount.setLayout(panelCountLayout);
        panelCountLayout.setHorizontalGroup(
            panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCountLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHadir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAlpa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelIzin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSakit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCountLayout.setVerticalGroup(
            panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addComponent(labelHadir)
                .addComponent(jLabel4)
                .addComponent(labelAlpa)
                .addComponent(jLabel6)
                .addComponent(labelIzin)
                .addComponent(jLabel8)
                .addComponent(labelSakit))
        );

        labelTgl.setText("Tanggal : ");

        detailBtn.setText("Detail");
        detailBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(absenToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTgl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detailBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(absenToggle)
                    .addComponent(jLabel1)
                    .addComponent(comboJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTgl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(detailBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listKelasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listKelasValueChanged
        if (listKelas.getSelectedIndex() <= -1 || listKelas.getSelectedIndex() > listKelas.getMaxSelectionIndex()) {
            JOptionPane.showMessageDialog(this,
                    "Pilih kelas!",
                    "Load data error!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String namaKelas = listKelas.getSelectedValue().toString();
            int kodeKelas = 0;
            if (namaKelas != null) {
                for (int i = 0; i < NamaKelas.length; i++) {
                    if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                        kodeKelas = KodeKelas[i];
                    }
                }
            }
            if (!absenToggle.isSelected()) {
                loadDataSiswa(kodeKelas);
                panelCount.setVisible(false);
            } else {
                if (comboDate != null) {
                    loadDataSiswa(kodeKelas);
                    loadAbsen(comboDate.getDate(), kodeKelas);
                    cekJam();
                    cekKeterangan();
                }
            }
        }
    }//GEN-LAST:event_listKelasValueChanged

    private void listKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listKelasMouseClicked
        if (listKelas.getSelectedIndex() <= -1 || listKelas.getSelectedIndex() > listKelas.getMaxSelectionIndex()) {
            JOptionPane.showMessageDialog(this,
                    "Pilih kelas!",
                    "Load data error!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String namaKelas = listKelas.getSelectedValue().toString();
            int kodeKelas = 0;
            if (namaKelas != null) {
                for (int i = 0; i < NamaKelas.length; i++) {
                    if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                        kodeKelas = KodeKelas[i];
                    }
                }
            }
            if (!absenToggle.isSelected()) {
                loadDataSiswa(kodeKelas);
                panelCount.setVisible(false);
            } else {
                if (comboDate != null) {
                    loadDataSiswa(kodeKelas);
                    loadAbsen(comboDate.getDate(), kodeKelas);
                    cekJam();
                    cekKeterangan();
                }
            }
        }
    }//GEN-LAST:event_listKelasMouseClicked

    private void absenToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_absenToggleActionPerformed
        if (!absenToggle.isSelected()) {
            if (listKelas.getSelectedIndex() <= -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih kelas!",
                        "Load data error!",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                String namaKelas = listKelas.getSelectedValue().toString();
                int kodeKelas = 0;
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                if (!absenToggle.isSelected()) {
                    loadDataSiswa(kodeKelas);
                    panelCount.setVisible(false);
                } else {
                    if (comboDate.getDate() != null) {
                        loadDataSiswa(kodeKelas);
                        loadAbsen(comboDate.getDate(), kodeKelas);
                        cekJam();
                        cekKeterangan();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Pilih tanggal absensi!",
                                "Load absen error!",
                                JOptionPane.ERROR_MESSAGE);
                        absenToggle.setSelected(false);
                        labelTgl.setForeground(new java.awt.Color(255, 0, 0));
                    }
                }
            }
        } else {
            if (listKelas.getSelectedIndex() <= -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih kelas!",
                        "Load data error!",
                        JOptionPane.ERROR_MESSAGE);
                absenToggle.setSelected(false);
            } else {
                String namaKelas = listKelas.getSelectedValue().toString();
                int kodeKelas = 0;
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                if (comboDate.getDate() != null) {
                    loadDataSiswa(kodeKelas);
                    loadAbsen(comboDate.getDate(), kodeKelas);
                    cekJam();
                    cekKeterangan();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Pilih tanggal absensi!",
                            "Load absen error!",
                            JOptionPane.ERROR_MESSAGE);
                    absenToggle.setSelected(false);
                    labelTgl.setForeground(new java.awt.Color(255, 0, 0));
                }
            }
        }
    }//GEN-LAST:event_absenToggleActionPerformed

    private void comboJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJamActionPerformed
        if (comboDate.getDate() != null) {
            cekJam();
            cekKeterangan();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Pilih tanggal!",
                    "Load absen error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_comboJamActionPerformed

    private void comboDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateActionPerformed
        labelTgl.setForeground(new java.awt.Color(0, 0, 0));
        if (!absenToggle.isSelected()) {
            if (listKelas.getSelectedIndex() <= -1) {
                JOptionPane.showMessageDialog(this,
                        "Pilih kelas!",
                        "Load data error!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String namaKelas = listKelas.getSelectedValue().toString();
                int kodeKelas = 0;
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                if (!absenToggle.isSelected()) {
                    loadDataSiswa(kodeKelas);
                    panelCount.setVisible(false);
                } else {
                    if (comboDate != null) {
                        loadDataSiswa(kodeKelas);
                        loadAbsen(comboDate.getDate(), kodeKelas);
                        cekJam();
                        cekKeterangan();
                    }
                }
            }
        } else {
            String namaKelas = listKelas.getSelectedValue().toString();
            int kodeKelas = 0;
            if (namaKelas != null) {
                for (int i = 0; i < NamaKelas.length; i++) {
                    if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                        kodeKelas = KodeKelas[i];
                    }
                }
            }
            if (comboDate != null) {
                loadDataSiswa(kodeKelas);
                loadAbsen(comboDate.getDate(), kodeKelas);
                cekJam();
                cekKeterangan();
            }
        }
    }//GEN-LAST:event_comboDateActionPerformed

    private void detailBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailBtnActionPerformed
        int i = Tabel.getSelectedRow();
        Detail detailForm = new Detail();
        detailForm.comboNIS.setModel(VarNIS);
        String namaKelas = listKelas.getSelectedValue().toString();
        int kodeKelas = 0;
        if (namaKelas != null) {
            for (int j = 0; j < NamaKelas.length; j++) {
                if (NamaKelas[j] != null && NamaKelas[j].equals(namaKelas)) {
                    kodeKelas = KodeKelas[j];
                }
            }
        }
        detailForm.kodeKelas = kodeKelas;
        if (i > -1) {
            String selectedNIS = Tabel.getModel().getValueAt(i, 0).toString();
            detailForm.comboNIS.setSelectedItem(selectedNIS);
        }
        detailForm.setVisible(true);
    }//GEN-LAST:event_detailBtnActionPerformed

    private void cekKeterangan() {
        panelCount.setVisible(true);
        int hadir = 0, alpa = 0, izin = 0, sakit = 0;
        int j = Integer.parseInt(comboJam.getSelectedItem().toString()) + 2;
        for (int i = 0; i < Tabel.getRowCount(); i++) {
            switch (Tabel.getModel().getValueAt(i, j).toString()) {
                case "Hadir":
                    hadir++;
                    break;
                case "Alpa":
                    alpa++;
                    break;
                case "Izin":
                    izin++;
                    break;
                case "Sakit":
                    sakit++;
                    break;
            }
        }
        labelHadir.setText(String.valueOf(hadir));
        labelAlpa.setText(String.valueOf(alpa));
        labelIzin.setText(String.valueOf(izin));
        labelSakit.setText(String.valueOf(sakit));
    }

    private void cekJam() {
        int jam = Integer.parseInt(comboJam.getSelectedItem().toString());
        String namaKelas = listKelas.getSelectedValue().toString();
        int kodeKelas = 0;
        switch (jam) {
            case 1:
                model2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "NIS", "Nama", "Jenis Kelamin", "Jam 1", "Jam 2", "Jam 3", "Jam 4"
                        }
                ) {
                    Class[] types = new Class[]{
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, true, false, false, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };

                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                loadDataSiswa(kodeKelas);
                loadAbsen(comboDate.getDate(), kodeKelas);
                break;
            case 2:
                model2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "NIS", "Nama", "Jenis Kelamin", "Jam 1", "Jam 2", "Jam 3", "Jam 4"
                        }
                ) {
                    Class[] types = new Class[]{
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, true, false, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                loadDataSiswa(kodeKelas);
                loadAbsen(comboDate.getDate(), kodeKelas);
                break;
            case 3:
                model2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "NIS", "Nama", "Jenis Kelamin", "Jam 1", "Jam 2", "Jam 3", "Jam 4"
                        }
                ) {
                    Class[] types = new Class[]{
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, true, false
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                loadDataSiswa(kodeKelas);
                loadAbsen(comboDate.getDate(), kodeKelas);
                break;
            case 4:
                model2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "NIS", "Nama", "Jenis Kelamin", "Jam 1", "Jam 2", "Jam 3", "Jam 4"
                        }
                ) {
                    Class[] types = new Class[]{
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class, java.lang.String.class, java.lang.String.class,
                        java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false, true
                    };

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                if (namaKelas != null) {
                    for (int i = 0; i < NamaKelas.length; i++) {
                        if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                            kodeKelas = KodeKelas[i];
                        }
                    }
                }
                loadDataSiswa(kodeKelas);
                loadAbsen(comboDate.getDate(), kodeKelas);
                break;
        }
    }

    public String getJenisKelamin(int NIS) {
        String jenisKelamin = null;
        for (int i = 0; i < countSiswa; i++) {
            if (NIS == this.NIS[i]) {
                if (JenisKelamin[i] == true) {
                    jenisKelamin = "Laki-laki";
                } else {
                    jenisKelamin = "Perempuan";
                }
            }
        }
        return jenisKelamin;
    }

    public String getNamaSiswa(int NIS) {
        String namaSiswa = null;
        for (int i = 0; i < countSiswa; i++) {
            if (NIS == this.NIS[i]) {
                namaSiswa = NamaSiswa[i];
            }
        }
        return namaSiswa;
    }

    public String getNamaKelas(int kodeKelas) {
        String namaKelas = null;
        for (int i = 0; i < countKelas; i++) {
            if (kodeKelas == this.KodeKelas[i]) {
                namaKelas = NamaKelas[i];
            }
        }
        return namaKelas;
    }

    private int getNIS(int kodeKelas) {
        int NIS = 0;
        for (int i = 0; i < countSiswa; i++) {
            if (kodeKelas == this.KodeKelas[i]) {
                NIS = this.NIS[i];
            }
        }
        return NIS;
    }

    public int getkodeKelas(int NIS) {
        int kodeKelas = 0;
        for (int i = 0; i < countSiswa; i++) {
            if (NIS == this.NIS[i]) {
                kodeKelas = this.KodeKelas[i];
            }
        }
        return kodeKelas;
    }

    public int getkodeKelas(String namaKelas) {
        int kodeKelas = 0;
        if (namaKelas != null) {
            for (int i = 0; i < NamaKelas.length; i++) {
                if (NamaKelas[i] != null && NamaKelas[i].equals(namaKelas)) {
                    kodeKelas = KodeKelas[i];
                }
            }
        }
        return kodeKelas;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Absensi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set cross-platform Java L&F (also called "Metal")
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    // handle exception
                }
                new Absensi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabel;
    private javax.swing.JToggleButton absenToggle;
    private org.jdesktop.swingx.JXDatePicker comboDate;
    private javax.swing.JComboBox comboJam;
    private javax.swing.JButton detailBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAlpa;
    private javax.swing.JLabel labelHadir;
    private javax.swing.JLabel labelIzin;
    private javax.swing.JLabel labelSakit;
    private javax.swing.JLabel labelTgl;
    private javax.swing.JList listKelas;
    private javax.swing.JPanel panelCount;
    // End of variables declaration//GEN-END:variables
}
