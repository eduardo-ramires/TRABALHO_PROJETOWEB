import { test, expect } from '@playwright/test';

test('Componentes principais', async ({ page }) => {
  await page.goto(process.env.URL);

  await expect(page.getByText("Xis do Ramires")).toBeVisible();
  await expect(page.getByRole('button', { name: 'Sou uma Mesa' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Sou ADM' })).toBeVisible();
});

test.skip('Valida fluxo de "Sou uma mesa"', async ({ page }) => {
  await page.goto(process.env.URL);
  await expect(page.getByRole('button', { name: 'Sou uma Mesa' })).toBeVisible();
  page.getByRole('button', { name: 'Sou uma Mesa' }).click();

  await expect(page.getByText("Selecione sua mesa")).toBeVisible();
  await expect(page.getByRole('button', { name: 'Mesa 1' })).toBeVisible();
  page.getByRole('button', { name: 'Mesa 1' }).click();

  await page.waitForURL(/mesa/, { timeout: 10000 });
  await expect(page.getByText("Xis do Ramires")).toBeVisible();
});

test('Valida fluxo de "Sou ADM"', async ({ page }) => {
  await page.goto(process.env.URL);
  await expect(page.getByRole('button', { name: 'Sou ADM' })).toBeVisible();
  page.getByRole('button', { name: 'Sou ADM' }).click();

  await expect(page.getByText("Login ADM")).toBeVisible();
  await page.getByRole('textbox', { name: 'Nome' }).fill(process.env.USER_NAME);
  await page.getByRole('textbox', { name: 'Senha' }).fill(process.env.USER_PASSWORD);
  page.getByRole('button', { name: 'Entrar' }).click();

  await page.waitForURL(/adm/, { timeout: 10000 });
  await expect(page.getByText("Xis do Ramires")).toBeVisible();
});
