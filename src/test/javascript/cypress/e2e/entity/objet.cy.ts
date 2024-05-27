import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Objet e2e test', () => {
  const objetPageUrl = '/objet';
  const objetPageUrlPattern = new RegExp('/objet(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const objetSample = { nom: 'dearly', type: 'VOITURE', statut: 'RESERVE' };

  let objet;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/objets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/objets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/objets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (objet) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/objets/${objet.id}`,
      }).then(() => {
        objet = undefined;
      });
    }
  });

  it('Objets menu should load Objets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('objet');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Objet').should('exist');
    cy.url().should('match', objetPageUrlPattern);
  });

  describe('Objet page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(objetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Objet page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/objet/new$'));
        cy.getEntityCreateUpdateHeading('Objet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', objetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/objets',
          body: objetSample,
        }).then(({ body }) => {
          objet = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/objets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [objet],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(objetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Objet page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('objet');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', objetPageUrlPattern);
      });

      it('edit button click should load edit Objet page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Objet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', objetPageUrlPattern);
      });

      it('edit button click should load edit Objet page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Objet');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', objetPageUrlPattern);
      });

      it('last delete button click should delete instance of Objet', () => {
        cy.intercept('GET', '/api/objets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('objet').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', objetPageUrlPattern);

        objet = undefined;
      });
    });
  });

  describe('new Objet page', () => {
    beforeEach(() => {
      cy.visit(`${objetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Objet');
    });

    it('should create an instance of Objet', () => {
      cy.get(`[data-cy="nom"]`).type('once though');
      cy.get(`[data-cy="nom"]`).should('have.value', 'once though');

      cy.get(`[data-cy="description"]`).type('hm');
      cy.get(`[data-cy="description"]`).should('have.value', 'hm');

      cy.get(`[data-cy="type"]`).select('MAISON');

      cy.get(`[data-cy="statut"]`).select('RESERVE');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        objet = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', objetPageUrlPattern);
    });
  });
});
