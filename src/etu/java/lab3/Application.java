package etu.java.lab3;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Program for cinema
 *
 * @author Rodion
 * @version 1.0
 * @since 2024
 */

public class Application {
    private JFrame window;
    private JToolBar ButPanel;
    private JButton save;
    private JButton open;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JButton info;
    private JButton filter;
    private DefaultTableModel model;
    private JTable films;
    private JComboBox Name;
    private JTextField filmName;
    private JPanel filterPanel;

    public void show() {
        window = new JFrame ("Список фильмов");

        ButPanel = new JToolBar();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        save = new JButton(new ImageIcon("./src/Icons/save-20x20.png"));
        open = new JButton(new ImageIcon("./src/Icons/open-20x20.png"));
        add = new JButton(new ImageIcon("./src/Icons/add-20x20.png"));
        edit = new JButton(new ImageIcon("./src/Icons/edit-20x20.jpg"));
        delete = new JButton(new ImageIcon("./src/Icons/trash-20x20.png"));
        info = new JButton(new ImageIcon("./src/Icons/info-20x20.png"));

        ButPanel.add(save);
        ButPanel.add(open);
        ButPanel.add(add);
        ButPanel.add(edit);
        ButPanel.add(delete);
        ButPanel.add(info);

        window.getContentPane().add(BorderLayout.NORTH, ButPanel);

        String[] columns = {"Фильм", "Жанр", "Сеанс", "Проданные билеты"};
        Object[][] data = {
                {"Форсаж 2", "Боевик", "19:30", "147"},
                {"Стражи галактики 2", "Научная фантастика", "14:30", "182"},
                {"Матрица", "Научная фантастика", "17:00", "156"},
        };
        model = new DefaultTableModel(data, columns);
        films = new JTable(model);

        window.add(BorderLayout.CENTER, new JScrollPane(films));

        Name = new JComboBox(new String[]{"Фильм", "Жанр",
                "Сеанс"});
        filmName = new JTextField("Название фильма");
        filter = new JButton("Поиск");
        filterPanel = new JPanel();
        filterPanel.add(Name);
        filterPanel.add(filmName);
        filterPanel.add(filter);
        window.add(BorderLayout.SOUTH, filterPanel);

        add.addActionListener(new AddButtonListener());
        delete.addActionListener(new DeleteButtonListener());
        filter.addActionListener(new FilterButtonListener());

        window.setVisible(true);
    }

    public class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Получаем название фильма из текстового поля
            String name = filmName.getText();
            if (!name.isEmpty()) {
                // Добавляем новую строку в таблицу
                model.addRow(new Object[]{name, "Жанр", "Сеанс", "0"});
                JOptionPane.showMessageDialog(window, "Фильм \"" + name + "\" добавлен!");
            } else {
                JOptionPane.showMessageDialog(window, "Введите название фильма.");
            }
        }
    }

    public class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = films.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(window, "Фильм удален!");
            } else {
                JOptionPane.showMessageDialog(window, "Выберите фильм для удаления.");
            }
        }
    }

    public class FilterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchCriteria = filmName.getText().toLowerCase();
            String selectedOption = (String) Name.getSelectedItem();
            StringBuilder result = new StringBuilder("Результаты поиска:\n");
            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                String film = (String) model.getValueAt(i, 0);
                String genre = (String) model.getValueAt(i, 1);
                String time = (String) model.getValueAt(i, 2);

                if ((selectedOption.equals("Фильм") && film.toLowerCase().contains(searchCriteria)) ||
                        (selectedOption.equals("Жанр") && genre.toLowerCase().contains(searchCriteria)) ||
                        (selectedOption.equals("Сеанс") && time.toLowerCase().contains(searchCriteria))) {
                    result.append(film).append(", ").append(genre).append(", ").append(time).append("\n");
                    found = true;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(window, result.toString());
            } else {
                JOptionPane.showMessageDialog(window, "Фильм не найден.");
            }
        }
    }

    public static void main(String[] args) {
        new Application().show();
    }
}
