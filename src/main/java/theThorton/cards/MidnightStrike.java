package theThorton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

import static theThorton.ThortMod.makeCardPath;

public class MidnightStrike extends AbstractThortonCard {


    public static final String ID = ThortMod.makeID("MidnightStrike");
    public static final String IMG = makeCardPath("MidnightStrike.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int DAMAGE = 100;


    public MidnightStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.exhaust = true;
        this.tags.add(CardTags.STRIKE);
        baseMagicNumber = magicNumber = 11;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.magicNumber = baseMagicNumber = 11 - AbstractDungeon.actionManager.cardsPlayedThisCombat.size();

        if (magicNumber< 0) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[3];
        } else if (magicNumber == 0) {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[4];
        } else {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        }

        this.initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else if (magicNumber > 0 || magicNumber < 0) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[1] + this.magicNumber + EXTENDED_DESCRIPTION[2];
            return false;
        } else {
            return canUse;
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}