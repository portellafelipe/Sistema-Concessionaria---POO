import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TelaPrincipal extends JFrame {

    private final ClienteCrud clientes = new ClienteCrud();
    private final FuncionarioCrud funcionarios = new FuncionarioCrud();
    private final VeiculoCrud veiculos = new VeiculoCrud();
    private final VendaCrud vendas = new VendaCrud();

    public TelaPrincipal() {
        setTitle("AutoCar - Sistema da Concessionária");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Clientes", criarPainelClientes());
        abas.addTab("Funcionários", criarPainelFuncionarios());
        abas.addTab("Veículos", criarPainelVeiculos());
        abas.addTab("Vendas", criarPainelVendas());

        add(abas);
    }

    private JPanel painelBase() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return p;
    }

    // ==========================================
    // PAINEL DE CLIENTES
    // ==========================================
    private JPanel criarPainelClientes() {
        JPanel p = painelBase();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);

        JTextField id = criarCampoId();
        JTextField nome = new JTextField(12);
        JTextField cpf = new JTextField(10);
        JTextField telefone = new JTextField(10);
        JTextField email = new JTextField(14);

        JPanel campos = criarPainelCampos(
                new String[]{"ID", "Nome", "CPF", "Telefone", "E-mail"},
                new JTextField[]{id, nome, cpf, telefone, email}
        );

        Runnable carregar = () -> {
            model.clear();
            try {
                for (Cliente c : clientes.listar()) {
                    model.addElement(c.getId() + " | " + c.getNome() + " | " + c.getCpf());
                }
            } catch (Exception e) { erro(e); }
        };

        JButton inserir = new JButton("Cadastrar");
        JButton atualizar = new JButton("Atualizar");
        JButton excluir = new JButton("Excluir");
        JButton limpar = new JButton("Limpar");
        JButton recarregar = new JButton("Recarregar");

        inserir.addActionListener(e -> executar(() ->
                clientes.inserir(new Cliente(0, nome.getText(), telefone.getText(), cpf.getText(), email.getText())), carregar));

        atualizar.addActionListener(e -> executar(() ->
                clientes.atualizar(new Cliente(parseId(id), nome.getText(), telefone.getText(), cpf.getText(), email.getText())), carregar));

        excluir.addActionListener(e -> executar(() -> clientes.excluir(parseId(id)), carregar));

        limpar.addActionListener(e -> limparCampos(id, nome, cpf, telefone, email));
        recarregar.addActionListener(e -> carregar.run());

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && lista.getSelectedIndex() >= 0) {
                try {
                    Cliente c = clientes.listar().get(lista.getSelectedIndex());
                    id.setText(String.valueOf(c.getId()));
                    nome.setText(c.getNome());
                    cpf.setText(c.getCpf());
                    telefone.setText(c.getTelefone());
                    email.setText(c.getEmail());
                } catch (Exception ex) { erro(ex); }
            }
        });

        p.add(campos, BorderLayout.NORTH);
        p.add(new JScrollPane(lista), BorderLayout.CENTER);
        p.add(criarPainelBotoes(inserir, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);

        carregar.run();
        return p;
    }

    // ==========================================
    // PAINEL DE FUNCIONÁRIOS
    // ==========================================
    private JPanel criarPainelFuncionarios() {
        JPanel p = painelBase();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);

        JTextField id = criarCampoId();
        JTextField nome = new JTextField(12);
        JTextField matricula = new JTextField(8);
        JTextField cargo = new JTextField(10);
        JTextField telefone = new JTextField(10);

        JPanel campos = criarPainelCampos(
                new String[]{"ID", "Nome", "Matrícula", "Cargo", "Telefone"},
                new JTextField[]{id, nome, matricula, cargo, telefone}
        );

        Runnable carregar = () -> {
            model.clear();
            try {
                for (Funcionario f : funcionarios.listar()) {
                    model.addElement(f.getId() + " | " + f.getNome() + " | " + f.getMatricula());
                }
            } catch (Exception e) { erro(e); }
        };

        JButton inserir = new JButton("Cadastrar");
        JButton atualizar = new JButton("Atualizar");
        JButton excluir = new JButton("Excluir");
        JButton limpar = new JButton("Limpar");
        JButton recarregar = new JButton("Recarregar");

        inserir.addActionListener(e -> executar(() ->
                funcionarios.inserir(new Funcionario(0, nome.getText(), telefone.getText(), matricula.getText(), cargo.getText())), carregar));

        atualizar.addActionListener(e -> executar(() ->
                funcionarios.atualizar(new Funcionario(parseId(id), nome.getText(), telefone.getText(), matricula.getText(), cargo.getText())), carregar));

        excluir.addActionListener(e -> executar(() -> funcionarios.excluir(parseId(id)), carregar));

        limpar.addActionListener(e -> limparCampos(id, nome, matricula, cargo, telefone));
        recarregar.addActionListener(e -> carregar.run());

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && lista.getSelectedIndex() >= 0) {
                try {
                    Funcionario f = funcionarios.listar().get(lista.getSelectedIndex());
                    id.setText(String.valueOf(f.getId()));
                    nome.setText(f.getNome());
                    matricula.setText(f.getMatricula());
                    cargo.setText(f.getCargo());
                    telefone.setText(f.getTelefone());
                } catch (Exception ex) { erro(ex); }
            }
        });

        p.add(campos, BorderLayout.NORTH);
        p.add(new JScrollPane(lista), BorderLayout.CENTER);
        p.add(criarPainelBotoes(inserir, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);

        carregar.run();
        return p;
    }

    // ==========================================
    // PAINEL DE VEÍCULOS
    // ==========================================
    private JPanel criarPainelVeiculos() {
        JPanel p = painelBase();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);

        JTextField id = criarCampoId();
        JTextField marca = new JTextField(10);
        JTextField modelo = new JTextField(10);
        JTextField ano = new JTextField(5);
        JTextField preco = new JTextField(8);
        JTextField situacao = new JTextField("Disponível", 10);

        JPanel campos = criarPainelCampos(
                new String[]{"ID", "Marca", "Modelo", "Ano", "Preço", "Situação"},
                new JTextField[]{id, marca, modelo, ano, preco, situacao}
        );

        Runnable carregar = () -> {
            model.clear();
            try {
                for (Veiculo v : veiculos.listar()) {
                    model.addElement(v.getId() + " | " + v.getDescricao() + " | R$ " + v.getPreco() + " | " + v.getSituacao());
                }
            } catch (Exception e) { erro(e); }
        };

        JButton inserir = new JButton("Cadastrar");
        JButton atualizar = new JButton("Atualizar");
        JButton excluir = new JButton("Excluir");
        JButton limpar = new JButton("Limpar");
        JButton recarregar = new JButton("Recarregar");

        inserir.addActionListener(e -> executar(() ->
                veiculos.inserir(new Veiculo(0, marca.getText(), modelo.getText(), Integer.parseInt(ano.getText()), parseBigDecimal(preco.getText()), situacao.getText())), carregar));

        atualizar.addActionListener(e -> executar(() ->
                veiculos.atualizar(new Veiculo(parseId(id), marca.getText(), modelo.getText(), Integer.parseInt(ano.getText()), parseBigDecimal(preco.getText()), situacao.getText())), carregar));

        excluir.addActionListener(e -> executar(() -> veiculos.excluir(parseId(id)), carregar));

        limpar.addActionListener(e -> {
            limparCampos(id, marca, modelo, ano, preco);
            situacao.setText("Disponível");
        });
        recarregar.addActionListener(e -> carregar.run());

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && lista.getSelectedIndex() >= 0) {
                try {
                    Veiculo v = veiculos.listar().get(lista.getSelectedIndex());
                    id.setText(String.valueOf(v.getId()));
                    marca.setText(v.getMarca());
                    modelo.setText(v.getModelo());
                    ano.setText(String.valueOf(v.getAno()));
                    preco.setText(v.getPreco().toString());
                    situacao.setText(v.getSituacao());
                } catch (Exception ex) { erro(ex); }
            }
        });

        p.add(campos, BorderLayout.NORTH);
        p.add(new JScrollPane(lista), BorderLayout.CENTER);
        p.add(criarPainelBotoes(inserir, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);

        carregar.run();
        return p;
    }

    // ==========================================
    // PAINEL DE VENDAS
    // ==========================================
    private JPanel criarPainelVendas() {
        JPanel p = painelBase();

        JTextField clienteId = new JTextField(5);
        JTextField funcionarioId = new JTextField(5);
        JTextField veiculoId = new JTextField(5);
        JTextField valor = new JTextField(8);

        JPanel campos = criarPainelCampos(
                new String[]{"ID do cliente", "ID do funcionário", "ID do veículo", "Valor da venda"},
                new JTextField[]{clienteId, funcionarioId, veiculoId, valor}
        );

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);

        JButton registrar = new JButton("Registrar venda");
        JButton recarregar = new JButton("Recarregar vendas");

        Runnable carregar = () -> {
            model.clear();
            try {
                for (Venda v : vendas.listar()) {
                    model.addElement(v.getId() + " | " + v.getCliente().getNome() + " | " +
                            v.getVeiculo().getDescricao() + " | " + v.getData() + " | R$ " + v.getValor());
                }
            } catch (Exception e) { erro(e); }
        };

        registrar.addActionListener(e -> executar(() -> {
            int ci = Integer.parseInt(clienteId.getText());
            int fi = Integer.parseInt(funcionarioId.getText());
            int vi = Integer.parseInt(veiculoId.getText());

            Cliente c = clientes.listar().stream().filter(x -> x.getId() == ci).findFirst()
                    .orElseThrow(() -> new Exception("Cliente não encontrado."));
            Funcionario f = funcionarios.listar().stream().filter(x -> x.getId() == fi).findFirst()
                    .orElseThrow(() -> new Exception("Funcionário não encontrado."));
            Veiculo v = veiculos.listar().stream().filter(x -> x.getId() == vi).findFirst()
                    .orElseThrow(() -> new Exception("Veículo não encontrado."));

            if (!"Disponível".equalsIgnoreCase(v.getSituacao())) {
                throw new Exception("Veículo não está disponível.");
            }

            vendas.inserir(new Venda(0, c, f, v, LocalDate.now(), parseBigDecimal(valor.getText())));
        }, carregar));

        recarregar.addActionListener(e -> carregar.run());

        p.add(campos, BorderLayout.NORTH);
        p.add(new JScrollPane(lista), BorderLayout.CENTER);
        p.add(criarPainelBotoes(registrar, recarregar), BorderLayout.SOUTH);

        carregar.run();
        return p;
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE COMPONENTES
    // ==========================================

    private JTextField criarCampoId() {
        JTextField campo = new JTextField(4);
        campo.setEditable(false);
        campo.setBackground(new Color(230, 230, 230)); // Deixa visualmente claro que é desativado
        return campo;
    }

    private JPanel criarPainelCampos(String[] rotulos, JTextField[] campos) {
        JPanel painel = new JPanel(new GridLayout(2, rotulos.length, 5, 5));
        for (String rotulo : rotulos) {
            painel.add(new JLabel(rotulo));
        }
        for (JTextField campo : campos) {
            painel.add(campo);
        }
        return painel;
    }

    private JPanel criarPainelBotoes(JButton... botoes) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        for (JButton b : botoes) {
            painel.add(b);
        }
        return painel;
    }

    private void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private int parseId(JTextField campoId) {
        return Integer.parseInt(campoId.getText().trim());
    }

    private BigDecimal parseBigDecimal(String texto) {
        return new BigDecimal(texto.replace(",", "."));
    }

    private void executar(Acao acao, Runnable depois) {
        try {
            acao.executar();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
            depois.run();
        } catch (Exception e) {
            erro(e);
        }
    }

    private void erro(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private interface Acao {
        void executar() throws Exception;
    }
}