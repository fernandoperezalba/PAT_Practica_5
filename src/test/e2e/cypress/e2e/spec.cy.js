describe('Registro y login', () => {
  let email;
  const pass = 'aaaaaaA1';
  const name = 'Mr.Nombre'

  // guardamos el email en Cypress.env, que sino serían diferentes para cada test y el login no funcionaría
  Cypress.env('testEmail', email); // ← 💾 Guardar email para el siguiente test

  it('Registro correcto', () => {
    email = Date.now() + '@email.com';
    cy.visit('http://localhost:8080/registro.html')
    cy.get('[name="name"]').type(name)
    cy.get('[name="name"]').should('have.value', name)
    cy.get('[name="email"]').type(email)
    cy.get('[name="email"]').should('have.value', email)
    cy.get('[name="password"]').type(pass)
    cy.get('[name="password"]').should('have.value', pass)
    cy.get('[name="password2"]').type(pass)
    cy.get('[name="password2"]').should('have.value', pass)
    cy.contains('Registrarse').click()
    cy.url().should('include', '/login.html')
    cy.contains('¡Registrado! Prueba a entrar')
  })

  // TODO#13
  // Implementa el siguiente test E2E del frontend web para
  // verificar que se realiza el login correctamente con el usuario
  // previamente registrado
  it('Login correcto', () => {
    // Espera a que se registre el usuario en el test anterior
    expect(email).to.be.a('string'); // asegura que está definido
    cy.visit('http://localhost:8080/login.html')
    cy.get('[name="email"]').type(email)
    cy.get('[name="password"]').type(pass)
    cy.get('input[type="submit"]').click()
    cy.url().should('include', '/app.html')
    cy.get('#nombre-inicio').should('contain', name)
    cy.get('#tel-inicio').should('contain', 'USER')
    cy.get('#email-inicio').should('contain', email)
  })


})
