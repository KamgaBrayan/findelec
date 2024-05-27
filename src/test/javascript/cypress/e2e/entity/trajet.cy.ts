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

describe('Trajet e2e test', () => {
  const trajetPageUrl = '/trajet';
  const trajetPageUrlPattern = new RegExp('/trajet(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const trajetSample = {
    villeDepart: 'bitterly',
    villeArrivee: 'deceivingly aw among',
    dateDepart: '2024-05-27T17:21:01.243Z',
    nombrePlacesDisponibles: 12618,
  };

  let trajet;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/trajets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/trajets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/trajets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (trajet) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/trajets/${trajet.id}`,
      }).then(() => {
        trajet = undefined;
      });
    }
  });

  it('Trajets menu should load Trajets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('trajet');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Trajet').should('exist');
    cy.url().should('match', trajetPageUrlPattern);
  });

  describe('Trajet page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(trajetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Trajet page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/trajet/new$'));
        cy.getEntityCreateUpdateHeading('Trajet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', trajetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/trajets',
          body: trajetSample,
        }).then(({ body }) => {
          trajet = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/trajets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [trajet],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(trajetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Trajet page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('trajet');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', trajetPageUrlPattern);
      });

      it('edit button click should load edit Trajet page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Trajet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', trajetPageUrlPattern);
      });

      it('edit button click should load edit Trajet page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Trajet');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', trajetPageUrlPattern);
      });

      it('last delete button click should delete instance of Trajet', () => {
        cy.intercept('GET', '/api/trajets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('trajet').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', trajetPageUrlPattern);

        trajet = undefined;
      });
    });
  });

  describe('new Trajet page', () => {
    beforeEach(() => {
      cy.visit(`${trajetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Trajet');
    });

    it('should create an instance of Trajet', () => {
      cy.get(`[data-cy="villeDepart"]`).type('frankly block usually');
      cy.get(`[data-cy="villeDepart"]`).should('have.value', 'frankly block usually');

      cy.get(`[data-cy="villeArrivee"]`).type('bah');
      cy.get(`[data-cy="villeArrivee"]`).should('have.value', 'bah');

      cy.get(`[data-cy="dateDepart"]`).type('2024-05-27T13:29');
      cy.get(`[data-cy="dateDepart"]`).blur();
      cy.get(`[data-cy="dateDepart"]`).should('have.value', '2024-05-27T13:29');

      cy.get(`[data-cy="nombrePlacesDisponibles"]`).type('32753');
      cy.get(`[data-cy="nombrePlacesDisponibles"]`).should('have.value', '32753');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        trajet = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', trajetPageUrlPattern);
    });
  });
});
