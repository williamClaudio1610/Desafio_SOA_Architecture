import { useState, useEffect } from 'react';
import { criarClienteComConta, realizarMovimento, buscarExtrato, listarContas } from './api';
import './App.css';

const formatadorMoeda = new Intl.NumberFormat('pt-PT', { style: 'currency', currency: 'EUR' });

function formatarMoeda(valor) {
  const numero = Number(valor);
  return formatadorMoeda.format(Number.isFinite(numero) ? numero : 0);
}

const rotulosMovimento = {
  DEPOSITO: 'Depósito',
  LEVANTAMENTO: 'Levantamento',
  TRANSFERENCIA: 'Transferência',
};

function App() {
  const [aba, setAba] = useState('onboarding');
  const [contas, setContas] = useState([]);
  const [mensagem, setMensagem] = useState(null);

  useEffect(() => {
    carregarContas();
  }, []);

  async function carregarContas() {
    try {
      const lista = await listarContas();
      setContas(lista);
    } catch (err) {
      console.error(err);
    }
  }

  function mostrarMensagem(texto, tipo = 'sucesso') {
    setMensagem({ texto, tipo });
    setTimeout(() => setMensagem(null), 4000);
  }

  return (
    <div className="app">
      <header>
        <div className="marca">
          <span className="marca-icone">SB</span>
          <h1>Desafio SOA — Banco</h1>
        </div>
        <nav>
          <button className={aba === 'onboarding' ? 'ativo' : ''} onClick={() => setAba('onboarding')}>Criar cliente</button>
          <button className={aba === 'movimento' ? 'ativo' : ''} onClick={() => setAba('movimento')}>Movimento</button>
          <button className={aba === 'extrato' ? 'ativo' : ''} onClick={() => setAba('extrato')}>Extrato</button>
        </nav>
      </header>

      {mensagem && <div className={`toast ${mensagem.tipo}`}>{mensagem.texto}</div>}

      <main>
        {aba === 'onboarding' && (
          <Onboarding
            onSucesso={() => { carregarContas(); mostrarMensagem('Cliente e conta criados com sucesso!'); }}
            onErro={(msg) => mostrarMensagem(msg, 'erro')}
          />
        )}
        {aba === 'movimento' && (
          <Movimento
            contas={contas}
            onSucesso={() => { carregarContas(); mostrarMensagem('Movimento realizado com sucesso!'); }}
            onErro={(msg) => mostrarMensagem(msg, 'erro')}
          />
        )}
        {aba === 'extrato' && <Extrato contas={contas} />}
      </main>
    </div>
  );
}

function Onboarding({ onSucesso, onErro }) {
  const [form, setForm] = useState({ nome: '', email: '', telefone: '', tipoConta: 'CORRENTE', depositoInicial: '' });
  const [carregando, setCarregando] = useState(false);

  function atualizar(campo, valor) {
    setForm({ ...form, [campo]: valor });
  }

  async function enviar(e) {
    e.preventDefault();
    setCarregando(true);
    try {
      await criarClienteComConta({ ...form, depositoInicial: Number(form.depositoInicial) });
      setForm({ nome: '', email: '', telefone: '', tipoConta: 'CORRENTE', depositoInicial: '' });
      onSucesso();
    } catch (err) {
      onErro(err.message);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <form className="cartao" onSubmit={enviar}>
      <h2>Criar cliente + conta</h2>
      <p className="subtitulo">Preenche os dados para abrir uma nova conta.</p>
      <label>Nome
        <input placeholder="Nome completo" value={form.nome} onChange={(e) => atualizar('nome', e.target.value)} required />
      </label>
      <div className="campo-duplo">
        <label>Email
          <input type="email" placeholder="nome@exemplo.com" value={form.email} onChange={(e) => atualizar('email', e.target.value)} required />
        </label>
        <label>Telefone
          <input placeholder="+351 900 000 000" value={form.telefone} onChange={(e) => atualizar('telefone', e.target.value)} required />
        </label>
      </div>
      <label>Tipo de conta
        <select value={form.tipoConta} onChange={(e) => atualizar('tipoConta', e.target.value)}>
          <option value="CORRENTE">Corrente</option>
          <option value="POUPANCA">Poupança</option>
        </select>
      </label>
      <label>Depósito inicial
        <input type="number" min="0" step="0.01" placeholder="0,00" value={form.depositoInicial} onChange={(e) => atualizar('depositoInicial', e.target.value)} required />
      </label>
      <button type="submit" disabled={carregando}>{carregando ? 'A criar...' : 'Criar'}</button>
    </form>
  );
}

function Movimento({ contas, onSucesso, onErro }) {
  const [tipo, setTipo] = useState('DEPOSITO');
  const [contaOrigemId, setContaOrigemId] = useState('');
  const [contaDestinoId, setContaDestinoId] = useState('');
  const [valor, setValor] = useState('');
  const [descricao, setDescricao] = useState('');
  const [carregando, setCarregando] = useState(false);

  async function enviar(e) {
    e.preventDefault();
    setCarregando(true);
    try {
      const dados = { tipoMovimento: tipo, valor: Number(valor), contaOrigemId, descricao };
      if (tipo === 'TRANSFERENCIA') dados.contaDestinoId = contaDestinoId;

      await realizarMovimento(dados);
      setValor('');
      setDescricao('');
      onSucesso();
    } catch (err) {
      onErro(err.message);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <form className="cartao" onSubmit={enviar}>
      <h2>Realizar movimento</h2>
      <p className="subtitulo">Escolhe o tipo de operação e a conta a movimentar.</p>
      <label>Tipo
        <select value={tipo} onChange={(e) => setTipo(e.target.value)}>
          <option value="DEPOSITO">Depósito</option>
          <option value="LEVANTAMENTO">Levantamento</option>
          <option value="TRANSFERENCIA">Transferência</option>
        </select>
      </label>
      <label>Conta origem
        <select value={contaOrigemId} onChange={(e) => setContaOrigemId(e.target.value)} required>
          <option value="">Seleciona uma conta</option>
          {contas.map((c) => (
            <option key={c.id} value={c.numeroConta}>{c.numeroConta} — saldo {formatarMoeda(c.saldo)}</option>
          ))}
        </select>
      </label>
      {tipo === 'TRANSFERENCIA' && (
        <label>Conta destino
          <select value={contaDestinoId} onChange={(e) => setContaDestinoId(e.target.value)} required>
            <option value="">Seleciona uma conta</option>
            {contas.map((c) => (
              <option key={c.id} value={c.numeroConta}>{c.numeroConta} — saldo {formatarMoeda(c.saldo)}</option>
            ))}
          </select>
        </label>
      )}
      <div className="campo-duplo">
        <label>Valor
          <input type="number" min="0.01" step="0.01" placeholder="0,00" value={valor} onChange={(e) => setValor(e.target.value)} required />
        </label>
        <label>Descrição
          <input placeholder="Opcional" value={descricao} onChange={(e) => setDescricao(e.target.value)} />
        </label>
      </div>
      <button type="submit" disabled={carregando}>{carregando ? 'A processar...' : 'Confirmar'}</button>
    </form>
  );
}

function Extrato({ contas }) {
  const [numeroConta, setNumeroConta] = useState('');
  const [extrato, setExtrato] = useState(null);
  const [erro, setErro] = useState(null);

  async function buscar() {
    setErro(null);
    setExtrato(null);
    try {
      const dados = await buscarExtrato(numeroConta);
      setExtrato(dados);
    } catch (err) {
      setErro(err.message);
    }
  }

  return (
    <div className="cartao">
      <h2>Extrato</h2>
      <p className="subtitulo">Consulta o saldo e o histórico de movimentos de uma conta.</p>
      <label>Conta
        <select value={numeroConta} onChange={(e) => setNumeroConta(e.target.value)}>
          <option value="">Seleciona uma conta</option>
          {contas.map((c) => (
            <option key={c.id} value={c.numeroConta}>{c.numeroConta}</option>
          ))}
        </select>
      </label>
      <button onClick={buscar} disabled={!numeroConta}>Ver extrato</button>

      {erro && <p className="erro-texto">{erro}</p>}

      {extrato && (
        <div className="extrato">
          <div className="extrato-cabecalho">
            <p className="extrato-cliente"><strong>{extrato.cliente.nome}</strong> — conta {extrato.conta.numeroConta}</p>
            <div className="extrato-saldo">
              <span className="etiqueta">Saldo atual</span>
              <span className="valor">{formatarMoeda(extrato.conta.saldo)}</span>
            </div>
          </div>

          {extrato.movimentos.length === 0 ? (
            <p className="vazio">Ainda não há movimentos nesta conta.</p>
          ) : (
            <ul>
              {extrato.movimentos.map((m) => (
                <li key={m.id}>
                  <div className="movimento-info">
                    <span className="movimento-descricao">{m.descricao || rotulosMovimento[m.tipoMovimento] || m.tipoMovimento}</span>
                    <span className="movimento-data">{new Date(m.dataMovimento).toLocaleString('pt-PT')}</span>
                  </div>
                  <div className="movimento-valores">
                    <span className={`badge ${m.tipoMovimento.toLowerCase()}`}>{rotulosMovimento[m.tipoMovimento] || m.tipoMovimento}</span>
                    <span className="movimento-valor">{formatarMoeda(m.valor)}</span>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
}

export default App;
