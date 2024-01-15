import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame {
    private JPanel board;
    private ColorScheme scheme;
    private Tile tiles;
    private JLabel score;
    private JLabel status;
    private JButton restart;
    public Main() {

        scheme = new ColorScheme();
        tiles = new Tile();

        board = new JPanel();
        board.setPreferredSize(new Dimension(850, 900));
        drawBoard();
        add(board);

        board.setFocusable(true);
        board.addKeyListener(new MyKeyListener());

        restart.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiles.reset();
                updateBoard();
                //board.requestFocusInWindow();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void drawBoard() {
        score = new JLabel("Score: 0");
        status = new JLabel("In game");
        restart = new JButton("Restart");

        Box box = Box.createHorizontalBox();
        box.add(score);
        box.add(Box.createHorizontalStrut(100));
        box.add(status);
        box.add(Box.createHorizontalStrut(100));
        box.add(restart);

        score.setFont(new Font("Arial", Font.PLAIN, 35));
        status.setFont(new Font("Arial", Font.PLAIN, 24));
        restart.setFont(new Font("Arial", Font.PLAIN, 30));

        board.add(box);

        GridLayout gridLayout = new GridLayout(4,4);
        JPanel grid = new JPanel(gridLayout);

        gridLayout.setHgap(10);
        gridLayout.setVgap(10);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel cell = new JLabel();
                int value = tiles.getTile(i, j);
                if (value!=0){
                    cell.setText(String.valueOf(value));
                }
                cell.setPreferredSize(new Dimension(200, 200));
                cell.setOpaque(true);
                cell.setFont(new Font("Arial",Font.PLAIN, 40));
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setVerticalAlignment(SwingConstants.CENTER);
                cell.setBackground(scheme.colors.get(value));
                grid.add(cell);
            }
        }
        board.add(grid);
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String code = KeyEvent.getKeyText(e.getKeyCode());
            if (!status.getText().equals("GAME OVER")){
                switch (code) {
                    case "Up":
                        tiles.shiftUp();
                        break;
                    case "Down":
                        tiles.shiftDown();
                        break;
                    case "Left":
                        tiles.shiftLeft();
                        break;
                    case "Right":
                        tiles.shiftRight();
                        break;
                }
                if (tiles.generateNewTile()){
                    updateBoard();
                } else {
                    if (tiles.isNeedToMerge()){
                        updateBoard();
                    } else {
                        status.setText("GAME OVER");
                        status.setFont(new Font("Arial", Font.BOLD, 30));
                        status.setForeground(new Color(250, 0, 0));
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private void updateBoard() {
        board.requestFocusInWindow();

        score.setText("Score: " + tiles.score);
        status.setText("In game");
        status.setFont(new Font("Arial", Font.PLAIN, 24));
        status.setForeground(new Color(0,0,0));

        Component[] components = board.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                Component[] cells = ((JPanel) component).getComponents();
                for (int i = 0; i < cells.length; i++) {
                    int value = tiles.getTile(i / 4, i % 4);
                    JLabel cell = (JLabel) cells[i];
                    if (value != 0) {
                        cell.setText(String.valueOf(value));
                    } else {
                        cell.setText("");
                    }
                    cell.setBackground(scheme.colors.get(value));
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
