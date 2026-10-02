import {expect, test} from "@playwright/test";
import { faker } from '@faker-js/faker/locale/pt_BR';

test('Valida tela de adm', async ({ page }) => {
    await page.goto(process.env.URL);
    await expect(page.getByRole('button', { name: 'Sou ADM' })).toBeVisible();
    page.getByRole('button', { name: 'Sou ADM' }).click();

    await expect(page.getByText("Login ADM")).toBeVisible();
    await page.getByRole('textbox', { name: 'Nome' }).fill(process.env.USER_NAME);
    await page.getByRole('textbox', { name: 'Senha' }).fill(process.env.USER_PASSWORD);
    page.getByRole('button', { name: 'Entrar' }).click();

    await page.waitForURL(/adm/, { timeout: 10000 });
    await expect(page.getByText("Xis do Ramires")).toBeVisible();
    await expect(page.getByRole('button', { name: 'Sair' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Novo produto' })).toBeVisible();
    await expect(page.getByText('Produtos', { exact: true })).toBeVisible();
    await expect(page.getByText('Mesas & ADMs', { exact: true })).toBeVisible();
    await expect(page.getByText('Pedidos', { exact: true })).toBeVisible();
});

test('Cadastra novo produto', async ({ page }) => {
    const nome = faker.company.name();
    const categoria = faker.string.alpha(4).toUpperCase();
    await page.goto(process.env.URL);
    await expect(page.getByRole('button', { name: 'Sou ADM' })).toBeVisible();
    page.getByRole('button', { name: 'Sou ADM' }).click();

    await expect(page.getByText("Login ADM")).toBeVisible();
    await page.getByRole('textbox', { name: 'Nome' }).fill(process.env.USER_NAME);
    await page.getByRole('textbox', { name: 'Senha' }).fill(process.env.USER_PASSWORD);
    page.getByRole('button', { name: 'Entrar' }).click();

    await page.waitForURL(/adm/, { timeout: 10000 });
    await expect(page.getByText("Xis do Ramires")).toBeVisible();

    await page.getByRole('button', { name: 'Novo produto' }).click();
    await page.getByRole('textbox', { name: 'Nome' }).fill(nome);
    await page.getByRole('textbox', { name: 'Descrição' }).fill(faker.company.name());
    await page.getByRole('spinbutton', { name: 'Preço' }).fill('10');
    await page.getByRole('textbox', { name: 'Categoria' }).fill(categoria);
    await page.getByRole('textbox', { name: 'URL da imagem' }).fill(faker.internet.url());
    await page.getByRole('checkbox', { name: 'Produto disponível' }).uncheck();
    await page.getByRole('button', { name: 'Salvar Produto' }).click();

    const slugNome = nome.toLowerCase().replace(/\s+/g, '-');
    await expect(page.getByText(nome)).toBeVisible();
    await expect(page.getByText(categoria)).toBeVisible();
    await expect(page.getByTestId(`availability-button-${slugNome}`)).not.toBeChecked();

    await page.getByTestId(`delete-button-${slugNome}`).click();
    await expect(page.getByText(nome)).not.toBeVisible();
});

test('Edita, visualiza e exclui produto', async ({ page }) => {
    const nomeInicial = faker.company.name();
    const nomeEditado = faker.company.name();
    const categoria = faker.string.alpha(4).toUpperCase();
    const slug = (nome: string) => nome.toLowerCase().replace(/\s+/g, '-');

    await page.goto(process.env.URL);
    await expect(page.getByRole('button', { name: 'Sou ADM' })).toBeVisible();
    page.getByRole('button', { name: 'Sou ADM' }).click();

    await expect(page.getByText("Login ADM")).toBeVisible();
    await page.getByRole('textbox', { name: 'Nome' }).fill(process.env.USER_NAME);
    await page.getByRole('textbox', { name: 'Senha' }).fill(process.env.USER_PASSWORD);
    page.getByRole('button', { name: 'Entrar' }).click();

    await page.waitForURL(/adm/, { timeout: 10000 });
    await expect(page.getByText("Xis do Ramires")).toBeVisible();

    await page.getByRole('button', { name: 'Novo produto' }).click();
    await page.getByRole('textbox', { name: 'Nome' }).fill(nomeInicial);
    await page.getByRole('textbox', { name: 'Descrição' }).fill(faker.company.name());
    await page.getByRole('spinbutton', { name: 'Preço' }).fill('10');
    await page.getByRole('textbox', { name: 'Categoria' }).fill(categoria);
    await page.getByRole('textbox', { name: 'URL da imagem' }).fill(faker.internet.url());
    await page.getByRole('button', { name: 'Salvar Produto' }).click();
    await expect(page.getByText(nomeInicial)).toBeVisible();
    await expect(page.getByTestId(`availability-button-${slug(nomeInicial)}`)).toBeChecked();

    await page.getByTestId(`edit-button-${slug(nomeInicial)}`).click();
    await page.getByRole('textbox', { name: 'Nome' }).fill(nomeEditado);
    await page.getByRole('button', { name: 'Atualizar Produto' }).click();
    await expect(page.getByText(nomeEditado)).toBeVisible();
    await expect(page.getByTestId(`availability-button-${slug(nomeEditado)}`)).toBeChecked();

    await page.getByTestId(`view-button-${slug(nomeEditado)}`).click();
    await expect(page.getByRole('heading', { name: 'Visualizar Produto' })).toBeVisible();
    await page.getByRole('button', { name: 'X', exact: true }).click();

    await page.getByTestId(`delete-button-${slug(nomeEditado)}`).click();
    await expect(page.getByText(nomeEditado)).not.toBeVisible();
});
