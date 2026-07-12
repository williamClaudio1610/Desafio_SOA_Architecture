const BASE_URL = 'http://localhost:8085';

async function handleResponse(res) {
  const data = await res.json().catch(() => null);
  if (!res.ok) {
    throw new Error(data?.message || `Erro ${res.status}`);
  }
  return data;
}

export async function criarClienteComConta(dados) {
  const res = await fetch(`${BASE_URL}/api/orquestrador/onboarding/criar-cliente`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados),
  });
  return handleResponse(res);
}

export async function realizarMovimento(dados) {
  const res = await fetch(`${BASE_URL}/api/orquestrador/movimento`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados),
  });
  return handleResponse(res);
}

export async function buscarExtrato(numeroConta) {
  const res = await fetch(`${BASE_URL}/api/orquestrador/conta/${numeroConta}/extrato`);
  return handleResponse(res);
}

export async function listarContas() {
  const res = await fetch(`${BASE_URL}/api/contas/listarTodas`);
  return handleResponse(res);
}