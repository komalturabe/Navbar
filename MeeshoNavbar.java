import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MeeshoNavbar extends JFrame {
    private static final Color BRAND = new Color(159, 32, 137);
    private static final Color TEXT = new Color(53, 53, 67);
    private static final Color MUTED = new Color(117, 117, 130);
    private static final String SEARCH_HINT = "Try Saree, Kurti or Search by Product Code";

    public MeeshoNavbar() {
        super("Meesho-style Navigation Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 240));
        setSize(1200, 360);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(createMainRow());
        header.add(createCategoryRow());
        add(header, BorderLayout.NORTH);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(new Color(250, 250, 250));
        page.setBorder(new EmptyBorder(24, 42, 24, 42));
        JLabel heading = new JLabel("Products for everyone", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(TEXT);
        page.add(heading, BorderLayout.NORTH);
        add(page, BorderLayout.CENTER);
    }

    private JPanel createMainRow() {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(new EmptyBorder(14, 34, 14, 34));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 22);
        constraints.gridx = 0;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        JLabel logo = new JLabel("meesho");
        logo.setFont(new Font("SansSerif", Font.BOLD, 31));
        logo.setForeground(BRAND);
        row.add(logo, constraints);

        constraints.gridx++;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 0, 22);
        row.add(createSearchBox(), constraints);

        constraints.gridx++;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 0, 18);
        row.add(createAction("Become a Supplier", null), constraints);

        constraints.gridx++;
        constraints.insets = new Insets(0, 0, 0, 18);
        row.add(createAction("Download App", null), constraints);

        constraints.gridx++;
        constraints.insets = new Insets(0, 0, 0, 16);
        row.add(createAction("Profile", new UserIcon()), constraints);

        constraints.gridx++;
        constraints.insets = new Insets(0, 0, 0, 0);
        row.add(createAction("Cart", new CartIcon()), constraints);
        return row;
    }

    private JPanel createSearchBox() {
        JPanel search = new JPanel(new BorderLayout(10, 0));
        search.setBackground(Color.WHITE);
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 228)),
                new EmptyBorder(0, 12, 0, 12)));
        search.setPreferredSize(new Dimension(360, 46));
        search.setMinimumSize(new Dimension(180, 46));
        search.add(new JLabel(new SearchIcon()), BorderLayout.WEST);

        JTextField field = new JTextField(SEARCH_HINT);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(MUTED);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                if (field.getText().equals(SEARCH_HINT)) {
                    field.setText("");
                    field.setForeground(TEXT);
                }
            }

            @Override
            public void focusLost(FocusEvent event) {
                if (field.getText().isBlank()) {
                    field.setText(SEARCH_HINT);
                    field.setForeground(MUTED);
                }
            }
        });
        search.add(field, BorderLayout.CENTER);
        return search;
    }

    private JButton createAction(String label, Icon icon) {
        JButton button = new JButton(label, icon);
        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        if (icon == null) {
            button.setPreferredSize(new Dimension(label.startsWith("Become") ? 126 : 100, 44));
        } else {
            button.setPreferredSize(new Dimension(62, 52));
        }
        return button;
    }

    private JPanel createCategoryRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 23, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(235, 235, 239)),
                new EmptyBorder(13, 12, 13, 12)));

        String[] categories = {
            "Women Ethnic", "Women Western", "Men", "Kids", "Home & Kitchen",
            "Beauty & Health", "Jewellery & Accessories", "Bags & Footwear", "Electronics"
        };
        for (String category : categories) {
            JButton link = new JButton(category);
            link.setFont(new Font("SansSerif", Font.PLAIN, 13));
            link.setForeground(TEXT);
            link.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            link.setContentAreaFilled(false);
            link.setFocusPainted(false);
            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            row.add(link);
        }
        return row;
    }

    private abstract static class HeaderIcon implements Icon {
        protected final Color color = TEXT;

        @Override
        public int getIconWidth() {
            return 22;
        }

        @Override
        public int getIconHeight() {
            return 22;
        }

        protected Graphics2D prepare(Graphics graphics) {
            Graphics2D copy = (Graphics2D) graphics.create();
            copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            copy.setColor(color);
            return copy;
        }
    }

    private static class SearchIcon extends HeaderIcon {
        @Override
        public void paintIcon(java.awt.Component component, Graphics graphics, int x, int y) {
            Graphics2D copy = prepare(graphics);
            copy.drawOval(x + 3, y + 3, 12, 12);
            copy.drawLine(x + 14, y + 14, x + 20, y + 20);
            copy.dispose();
        }
    }

    private static class UserIcon extends HeaderIcon {
        @Override
        public void paintIcon(java.awt.Component component, Graphics graphics, int x, int y) {
            Graphics2D copy = prepare(graphics);
            copy.drawOval(x + 8, y + 2, 7, 7);
            copy.drawArc(x + 3, y + 10, 17, 12, 0, 180);
            copy.dispose();
        }
    }

    private static class CartIcon extends HeaderIcon {
        @Override
        public void paintIcon(java.awt.Component component, Graphics graphics, int x, int y) {
            Graphics2D copy = prepare(graphics);
            copy.drawLine(x + 2, y + 4, x + 5, y + 4);
            copy.drawLine(x + 5, y + 4, x + 8, y + 16);
            copy.drawLine(x + 8, y + 16, x + 19, y + 16);
            copy.drawLine(x + 7, y + 8, x + 20, y + 8);
            copy.drawLine(x + 20, y + 8, x + 18, y + 14);
            copy.drawOval(x + 8, y + 18, 3, 3);
            copy.drawOval(x + 16, y + 18, 3, 3);
            copy.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use Swing's default look and feel if the system one is unavailable.
            }
            new MeeshoNavbar().setVisible(true);
        });
    }
}

