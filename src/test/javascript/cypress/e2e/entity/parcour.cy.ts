import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Parcour e2e test', () => {
  const parcourPageUrl = '/parcour';
  const parcourPageUrlPattern = new RegExp('/parcour(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const parcourSample = {};

  let parcour;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parcours+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parcours').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parcours/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (parcour) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parcours/${parcour.id}`,
      }).then(() => {
        parcour = undefined;
      });
    }
  });

  it('Parcours menu should load Parcours page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('parcour');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Parcour').should('exist');
    cy.url().should('match', parcourPageUrlPattern);
  });

  describe('Parcour page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(parcourPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Parcour page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/parcour/new$'));
        cy.getEntityCreateUpdateHeading('Parcour');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', parcourPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parcours',
          body: parcourSample,
        }).then(({ body }) => {
          parcour = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parcours+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/parcours?page=0&size=20>; rel="last",<http://localhost/api/parcours?page=0&size=20>; rel="first"',
              },
              body: [parcour],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(parcourPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Parcour page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('parcour');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', parcourPageUrlPattern);
      });

      it('last delete button click should delete instance of Parcour', () => {
        cy.intercept('GET', '/api/parcours/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('parcour').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', parcourPageUrlPattern);

        parcour = undefined;
      });
    });
  });

  describe('new Parcour page', () => {
    beforeEach(() => {
      cy.visit(`${parcourPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Parcour');
    });

    it('should create an instance of Parcour', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        parcour = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', parcourPageUrlPattern);
    });
  });
});
