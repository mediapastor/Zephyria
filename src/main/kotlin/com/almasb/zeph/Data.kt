package com.almasb.zeph

import com.almasb.zeph.character.char
import com.almasb.zeph.combat.Element.*
import com.almasb.zeph.combat.Experience
import com.almasb.zeph.item.*
import com.almasb.zeph.item.ArmorType.*
import com.almasb.zeph.item.WeaponType.*


/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
object Data {

    private val weapons = hashMapOf<Int, WeaponData>()
    private val armors = hashMapOf<Int, ArmorData>()

    // There is ever only one of these
    val hands by lazy { Weapon(getWeapon(4000)) }
    val hat by lazy { Armor(getArmor(5000)) }
    val clothes by lazy { Armor(getArmor(5001)) }
    val shoes by lazy { Armor(getArmor(5002)) }

    init {
        Weapon.javaClass.declaredMethods.forEach {
            val data = it.invoke(Weapon) as WeaponData

            weapons[data.description.id] = data
        }

        Armor.javaClass.declaredMethods.forEach {
            val data = it.invoke(Armor) as ArmorData

            armors[data.description.id] = data
        }
    }

    fun isWeapon(id: Int) = id.toString().startsWith("4")
    fun isArmor(id: Int) = id.toString().startsWith("5")

    fun getWeapon(id: Int) = weapons[id] ?: throw IllegalArgumentException("No weapon found: $id")
    fun getArmor(id: Int) = armors[id] ?: throw IllegalArgumentException("No armor found: $id")

    fun getDefaultArmor(id: Int): com.almasb.zeph.item.Armor {
        return when (id) {
            5000 -> hat
            5001 -> clothes
            5002 -> shoes
            else -> throw RuntimeException("")
        }
    }

    object Weapon {

        fun HANDS() = weapon {
            desc {
                id = 4000
                name = "Hands"
                description = "That's right, go kill everyone with your bare hands."
                textureName = "items/weapons/hands.png"
            }

            type = MACE
        }

        fun KNIFE() = weapon {
            desc {
                id = 4001
                name = "Knife"
                description = "A simple knife with poor blade."
                textureName = "items/weapons/knife.png"
            }

            type = DAGGER
            pureDamage = 15
        }

        fun IRON_SWORD() = weapon {
            desc {
                id = 4100
                name = "Iron Sword"
                description = "A standard warrior's sword with decent attack damage."
                textureName = "items/weapons/iron_sword.png"
            }

            type = ONE_H_SWORD
            pureDamage = 25
        }
    }

    object Armor {

        fun HAT() = armor {
            desc {
                id = 5000
                name = "Hat"
                description = "Ordinary hat, already out of fashion."
                textureName = "items/armor/hat.png"
            }

            armorType = HELM
        }

        fun CLOTHES() = armor {
            desc {
                id = 5001
                name = "Clothes"
                description = "Just normal clothes, don't count on any defense."
                textureName = "items/armor/clothes.png"
            }

            armorType = BODY
        }

        fun SHOES() = armor {
            desc {
                id = 5002
                name = "Shoes"
                description = "Some old shoes."
                textureName = "items/armor/shoes.png"
            }

            armorType = SHOES
        }
    }

    object Character {

        fun SKELETON_ARCHER() = char {
            desc {
                id = 2004
                name = "Skeleton-Archer"
                description = "Skeleton-Archer Description."
                textureName = "chars/enemies/skeleton_archer.png"
            }

            baseLevel = 2
            element = EARTH
            rewardXP = Experience(10, 3, 3)

            attributes {
                str = 5
                vit = 30
                dex = 3
                agi = 2
            }

            // drop items
            4001 has 50
        }
    }







//
//    fun getItem(id: Int): Entity {
//        if (weapons.containsKey(id))
//            return getWeapon(id)
//
//        if (armor.containsKey(id))
//            return getArmor(id)
//
//        throw IllegalArgumentException("ID $id not found in the database")
//    }
//
//    fun makeHPBar(): ProgressBar {
//        val bar = ProgressBar(false)
//
//        with(bar) {
//            setHeight(25.0)
//            setFill(Color.GREEN.brighter())
//            setTraceFill(Color.GREEN.brighter())
//            isLabelVisible = true
//        }
//
//        return bar
//    }
//
//    fun makeSkillBar(): ProgressBar {
//        val bar = ProgressBar(false)
//
//        with(bar) {
//            setHeight(25.0)
//            setFill(Color.BLUE.brighter().brighter())
//            setTraceFill(Color.BLUE)
//            isLabelVisible = true
//        }
//
//        return bar
//    }
//
//    fun makeCharacterSubView(entity: CharacterEntity): SubViewComponent {
//        val barHP = makeHPBar()
//        val barSP = makeSkillBar()
//
//        barHP.translateX = 0.0
//        barHP.translateY = 80.0
//        barHP.setWidth(Config.tileSize.toDouble())
//        barHP.setHeight(10.0)
//        barHP.isLabelVisible = false
//
//        barSP.translateX = 0.0
//        barSP.translateY = 90.0
//        barSP.setWidth(Config.tileSize.toDouble())
//        barSP.setHeight(10.0)
//        barSP.isLabelVisible = false
//
//        barHP.maxValueProperty().bind(entity.hp.maxValueProperty())
//        barHP.currentValueProperty().bind(entity.hp.valueProperty())
//
//        barSP.maxValueProperty().bind(entity.sp.maxValueProperty())
//        barSP.currentValueProperty().bind(entity.sp.valueProperty())
//
//        val text = Text()
//        text.font = Font.font(14.0)
//        text.fill = Color.WHITE
//        text.textProperty().bind(entity.description.name.concat(" Lv. ").concat(entity.baseLevel))
//        text.translateX = Config.tileSize.toDouble() / 2 - text.layoutBounds.width / 2
//        text.translateY = 75.0
//
//        val vbox = Group(barHP, barSP, text)
//
//        val subView = EntityView()
//        subView.addNode(vbox)
//
//        subView.translateXProperty().bind(entity.positionComponent.xProperty())
//        subView.translateYProperty().bind(entity.positionComponent.yProperty())
//
//        return SubViewComponent(subView)
//    }
//
//    fun createCharacter(dataComponents: List<Component>, x: Int, y: Int): CharacterEntity {
//        val char = CharacterEntity(dataComponents)
//        char.typeComponent.value = EntityType.CHARACTER
//        char.positionComponent.value = Point2D(x * Config.tileSize.toDouble(), y * Config.tileSize.toDouble())
//
//        char.addComponent(makeCharacterSubView(char))
//
//        return char
//    }




//
//        // DAGGERS 4000+
//

//
//        fun GUT_RIPPER() = listOf<Component>(
//                Description(4003, "The Gut Ripper", "A fierce weapon that punctures and ruptures enemies with vicious and lightning fast blows.", "items/weapons/gut_ripper.png"),
//                WeaponDataComponent(ItemLevel.EPIC, WeaponType.DAGGER, 100)
//                        .withRune(Rune(Attribute.AGILITY, 4))
//                        .withRune(Rune(Attribute.DEXTERITY, 4))
//                        .withRune(Rune(Attribute.LUCK, 1))
//        )
//
//        //        public static final String PRACTICE_SWORD = "A basic one-handed sword";
//        //        public static final String HALLSTATT_SWORD = "A sword favored by gladiators, it is especially designed for battles against armored enemies";
//        //        public static final String KAMPILAN_SWORD = "A thin sword designed to be easily bent, light, and very elastic";
//        //        public static final String MACHETE = "A strong cleaver-like sword";
//        //        public static final String TEGA_SWORD = "A ceremonial sword used by gravekeeper's to lead the dead to the great beyond";
//        //        public static final String SCHIAVONA_SWORD = "A popular sword among mercenary soldiers";
//        //        public static final String COLICHERMARDE_SWORD = "Small sword with good parrying characteristics";
//
//        // 1H SWORDS 4100
//

//
//        fun GETSUGA_TENSHO() = listOf<Component>(
//                Description(4101, "Getsuga Tensho", "A powerful sword that is carved from the fangs of the moon itself and pierced through heaven.", "items/weapons/getsuga_tensho.png"),
//                WeaponDataComponent(ItemLevel.EPIC, WeaponType.ONE_H_SWORD, 150)
//                        .withRune(Rune(Attribute.STRENGTH, 5))
//                        .withRune(Rune(Attribute.AGILITY, 4))
//                        .withRune(Rune(Attribute.DEXTERITY, 4))
//                        .withRune(Rune(Attribute.LUCK, 1))
//        )
//
//        // 2H SWORDS 4200
//
//        fun CLAYMORE() = listOf<Component>(
//                Description(4200, "Claymore", "Large, double-edged broad sword that was used by the Scottish highlanders.", "items/weapons/claymore.png"),
//                WeaponDataComponent(ItemLevel.NORMAL, WeaponType.TWO_H_SWORD, 35)
//                        .withRune(Rune(Attribute.STRENGTH, 1))
//        )
//
//        fun BROADSWORD() = listOf<Component>(
//                Description(4201, "Broadsword", "A sword with a wide, double sided blade.", "items/weapons/broadsword.png"),
//                WeaponDataComponent(ItemLevel.NORMAL, WeaponType.TWO_H_SWORD, 28)
//                        .withRune(Rune(Attribute.LUCK, 1))
//        )
//
//        fun FROSTMOURN() = listOf<Component>(
//                Description(4202, "Frostmourn", "The legendary sword of the Ice Dungeon's King.", "items/weapons/frostmourn.png"),
//                WeaponDataComponent(ItemLevel.EPIC, WeaponType.TWO_H_SWORD, 130)
//                        .withRune(Rune(Attribute.DEXTERITY, 5))
//                        .withRune(Rune(Attribute.STRENGTH, 3))
//        )
//
//        fun BATTLESWORD() = listOf<Component>(
//                Description(4203, "Battlesword", "A terrifying two-handed sword that is said to stimulate the nerves in order to kill, once it's in the wearer's hands.", "items/weapons/battlesword.png"),
//                WeaponDataComponent(ItemLevel.NORMAL, WeaponType.TWO_H_SWORD, 44)
//                        .withRune(Rune(Attribute.STRENGTH, 2))
//        )
//
//        fun LONGSWORD() = listOf<Component>(
//                Description(4204, "Longsword", "A two-handed sword with straight double-edged blade.", "items/weapons/longsword.png"),
//                WeaponDataComponent(ItemLevel.NORMAL, WeaponType.TWO_H_SWORD, 33)
//                        .withRune(Rune(Attribute.AGILITY, 1))
//                        .withRune(Rune(Attribute.DEXTERITY, 2))
//        )
//
//        // 1H AXES 4300
//
//        // 2H AXES 4400
//        fun SOUL_REAPER() = listOf<Component>(
//                Description(4300, "Soul Reaper", "Forged in the dephts of Aesmir, it is said the weilder can feel the weapon crave the souls of its enemies.", "items/weapons/soul_reaper.png"),
//                WeaponDataComponent(ItemLevel.EPIC, WeaponType.TWO_H_AXE, 170)
//                        .withRune(Rune(Attribute.STRENGTH, 7))
//                        .withRune(Rune(Attribute.VITALITY, 4))
//                        .withRune(Rune(Attribute.DEXTERITY, 2))
//        )
//
//        // MACES 4500
//
//        // KATARS 4600
//
//        // SPEARS 4700
//
//        // BOWS 4800
//
//        fun DRAGON_CLAW() = listOf<Component>(
//                Description(4800, "Dragon's Claw", "A mythical bow made of claws of the legendary dragon. Contains dragon's wisdom and loyal to only one master throughout his whole life. Grants dragon's and earlier owner's wisdom and knowledge to the new master.", "items/weapons/dragon_claw.png"),
//                WeaponDataComponent(ItemLevel.EPIC, WeaponType.BOW, 130)
//                        .withRune(Rune(Attribute.WISDOM, 3))
//                        .withRune(Rune(Attribute.DEXTERITY, 4))
//                        .withRune(Rune(Attribute.LUCK, 1))
//                        .withElement(Element.FIRE)
//        )
//
//        // RODS 4900
//    }
//



//
//        // BODY ARMOR 5100
//
//        fun CHAINMAIL() = listOf<Component>(
//                Description(5003, "Chainmail", "Armour consisting of small metal rings linked together in a pattern to form a mesh.", "items/armor/chainmail.png"),
//                ArmorDataComponent(ItemLevel.NORMAL, ArmorType.BODY, 10, 5)
//                        .withRune(Rune(Attribute.STRENGTH, 2))
//        )
//
//        fun SOUL_BARRIER() = listOf<Component>(
//                Description(5004, "Soul Barrier", "Protects its wearer from magic attacks.", "items/armor/soul_barrier.png"),
//                ArmorDataComponent(ItemLevel.UNIQUE, ArmorType.BODY, 10, 50)
//                        .withRune(Rune(Attribute.WILLPOWER, 2))
//        )
//
//        fun DOMOVOI() = listOf<Component>(
//                Description(5005, "Domovoi", "Generations of guardians have bled in this armour, imbuing it with spirits of protection. Spirits that awaken when the wearers need is greatest.", "items/armor/domovoi.png"),
//                ArmorDataComponent(ItemLevel.UNIQUE, ArmorType.BODY, 15, 35)
//                        .withRune(Rune(Attribute.WILLPOWER, 3))
//        )
//
//        fun SAPPHIRE_LEGION_PLATE_ARMOR() = listOf<Component>(
//                Description(5006, "Sapphire Legion Plate Armor", "Produced in the Jaded Forges of the Jewelled King, strictly for use by warriors who have proved their mastery of combat through decades of service.", "items/armor/sapphire_legion_plate_armor.png"),
//                ArmorDataComponent(ItemLevel.UNIQUE, ArmorType.BODY, 30, 10)
//                        .withRune(Rune(Attribute.VITALITY, 4))
//        )
//
//        fun THANATOS_BODY_ARMOR() = listOf<Component>(
//                Description(5007, "Thanatos Body Armor", "A shattered piece of Thanatos' legendary armor. Grants its user great constitution.", "items/armor/thanatos_body_armor.png"),
//                ArmorDataComponent(ItemLevel.EPIC, ArmorType.BODY, 50, 25)
//                        .withRune(Rune(Attribute.VITALITY, 5))
//                        .withRune(Rune(Attribute.PERCEPTION, 3))
//        )
//
//        // HELMETS 5200
//
//        // SHOES 5300
//    }
//

//
//    object Skill {
//
//        object Warrior {
//            fun ROAR() = listOf<Component>(
//                    Description(7010, "Roar", "Increases STR and VIT for the duration.", "skills/ic_skill_roar.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .onCast { caster, target, skill ->
//
//                                val effect = EffectEntity(listOf(
//                                        Description(7010, "Roar", "Roar", "effects/attr_up.png"),
//                                        EffectDataComponent(7.0)
//                                                .withRune(Rune(Attribute.STRENGTH, 3 * skill.level.value))
//                                                .withRune(Rune(Attribute.VITALITY, 2 * skill.level.value))
//                                ))
//
//                                caster.charConrol.addEffect(effect)
//
//                                SkillUseResult.NONE
//                            }
//                            .withMana(10)
//                            .withCooldown(14.0)
//            )
//
//            fun MIGHTY_SWING() = listOf<Component>(
//                    Description(7011, "Mighty Swing", "Physical attack. Damage is greater if you have more STR than your target.", "skills/ic_skill_mighty_swing.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .onCast { caster, target, skill ->
//                                val diff = caster.attributes.getTotalAttribute(Attribute.STRENGTH) - target.attributes.getTotalAttribute(Attribute.STRENGTH)
//                                val dmg = (Math.max(diff, 0) + 10 * skill.level.value) * 5.0
//
//                                SkillUseResult(caster.charConrol.dealPhysicalDamage(target, dmg))
//                            }
//                            .withMana(25)
//                            .withCooldown(25.0)
//            )
//
//            // TODO: this will have to be reapplied as max HP can change at runtime but then it would cause
//            // huge GC problems, so need an efficient and simple data structure
//
//            fun WARRIOR_HEART() = listOf<Component>(
//                    Description(7012, "Warrior's Heart", "Passively increases max HP.", "skills/ic_skill_warrior_heart.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .onLearn { caster, skill ->
//                                skill.data.onCast(caster, caster, skill)
//
//                                caster.stats.statProperty(Stat.MAX_HP).addListener({ o, old, new ->
//                                    skill.data.onCast(caster, caster, skill)
//                                })
//
//                                skill.level.addListener({o, old, new ->
//                                    skill.data.onCast(caster, caster, skill)
//                                })
//                            }
//                            .onCast { caster, target, skill ->
//                                caster.stats.addBonusStat(Stat.MAX_HP, -skill.testValue)
//
//                                skill.testValue = (caster.stats.getBaseStat(Stat.MAX_HP) * 0.25 * skill.level.value).toInt()
//
//                                caster.stats.addBonusStat(Stat.MAX_HP, skill.testValue)
//
//                                SkillUseResult.NONE
//                            }
//            )
//
//            fun ARMOR_MASTERY() = listOf<Component>(
//                    Description(7013, "Armor Mastery", "Increases armor rating.", "skills/ic_skill_armor_mastery.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .onLearn { caster, skill ->
//                                skill.data.onCast(caster, caster, skill)
//
//                                skill.level.addListener({o, old, new ->
//                                    skill.data.onCast(caster, caster, skill)
//                                })
//                            }
//                            .onCast { caster, target, skill ->
//                                val factor = 2.0
//                                val value = (factor * skill.level.value).toInt()
//
//                                caster.stats.addBonusStat(Stat.ARM, -skill.testValue)
//
//                                skill.testValue = value
//
//                                caster.stats.addBonusStat(Stat.ARM, skill.testValue)
//
//                                SkillUseResult.NONE
//                            }
//            )
//        }
//
//        object Crusader {
//
//            fun HOLY_LIGHT() = listOf<Component>(
//                    Description(7110, "Holy Light", "Heals and increases VIT for the duration.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.RESTORE, EnumSet.of(SkillTargetType.ALLY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun FAITH() = listOf<Component>(
//                    Description(7111, "Faith", "Further increases bonus given by Heart of a Warrior skill.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun DIVINE_ARMOR() = listOf<Component>(
//                    Description(7112, "Divine Armor", "Further increases bonus given by Armor Mastery skill.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun PRECISION_STRIKE() = listOf<Component>(
//                    Description(7113, "Precision Strike", "Deals armor ignoring damage based on STR.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun LAST_STAND() = listOf<Component>(
//                    Description(7114, "Last Stand", "Deals double base damage for the duration.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//        }
//
//        object Gladiator {
//            fun BASH() = listOf<Component>(
//                    Description(7210, "Bash", "A powerful physical attack that stuns the target for 5 seconds", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun ENDURANCE() = listOf<Component>(
//                    Description(7211, "Endurance", "Takes less damage and regenerates HP faster for the duration.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun DOUBLE_EDGE() = listOf<Component>(
//                    Description(7212, "Double Edge", "Sacrifice % of HP to deal double that damage to target. Damage is pure.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun BLOODLUST() = listOf<Component>(
//                    Description(7213, "Bloodlust", "Increases ATK based on the missing % HP.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun SHATTER_ARMOR() = listOf<Component>(
//                    Description(7214, "Shatter Armor", "Decreases target's armor for the duration.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//        }
//
//        object Mage {
//            fun FIREBALL() = listOf<Component>(
//                    Description(7020, "Fireball", "Deals magic damage with fire element.", "skills/ic_skill_fireball.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .onCast { caster, target, skill ->
//
//                                val dmg = caster.stats.getTotalStat(Stat.MATK) + skill.level.value * 20
//
//                                SkillUseResult(caster.charConrol.dealMagicalDamage(target, dmg, Element.FIRE))
//                            }
//                            .withTextureName("projectiles/fireball.png")
//                            .withMana(5)
//                            .withCooldown(15.0)
//            )
//
//            fun ICE_SHARD() = listOf<Component>(
//                    Description(7021, "Ice Shard", "Deals magic damage with water element.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun AIR_SPEAR() = listOf<Component>(
//                    Description(7022, "Air Spear", "Deals magic damage with air element.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun EARTH_BOULDER() = listOf<Component>(
//                    Description(7023, "Earth Boulder", "Deals magic damage with earth element.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//        }
//
//        object Wizard {
//
//            fun MAGIC_MASTERY() = listOf<Component>(
//                    Description(7120, "Magic Mastery", "Passively increases INT and WIL.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun AMPLIFY_MAGIC() = listOf<Component>(
//                    Description(7121, "Amplify Magic", "Increases MATK for the duration.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun MENTAL_STRIKE() = listOf<Component>(
//                    Description(7122, "Mental Strike", "Deals pure damage based on MATK.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun THUNDERBOLT_FIRESTORM() = listOf<Component>(
//                    Description(7123, "Thunderbolt Firestorm", "Deals magic damage with air and fire element.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun ICICLE_AVALANCHE() = listOf<Component>(
//                    Description(7124, "Icicle Avalanche", "Deals magic damage with water and earth element.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//        }
//
//        object Enchanter {
//            //            public static final int MAGIC_SHIELD = 7220;
//            //            public static final int ASTRAL_PROTECTION = 7221;
//            //            public static final int MIND_BLAST = 7222;
//            //            public static final int CURSE_OF_WITCHCRAFT = 7223;
//            //            public static final int MANA_BURN = 7224;
//
//            //            public static final String MAGIC_SHIELD = "Increases Armor rating for the duration";
//            //            public static final String ASTRAL_PROTECTION = "Passively increases MDEF";
//            //            public static final String MIND_BLAST = "Drains % of target's SP. Increases mana cost of all target's skills";
//            //            public static final String CURSE_OF_WITCHCRAFT = "Target cannot use skills for the duration";
//            //            public static final String MANA_BURN = "Burns target's SP and deals damage based on the SP burnt";
//        }
//
//        object Scout {
//
//            fun TRICK_ATTACK() = listOf<Component>(
//                    Description(7030, "Trick Attack", "Deals physical damage and steals gold equal to damage dealt.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .onCast { caster, target, skill ->
//
//                                val dmg = caster.stats.getTotalStat(Stat.ATK) + skill.level.value * 2 * GameMath.random(5)
//
//                                var money = false
//                                if (caster is Entity) {
//                                    caster.playerControl.rewardMoney(dmg.toInt())
//                                    money = true
//                                }
//
//                                // TODO: somehow notify user that he got money from attack
//
//                                SkillUseResult(caster.charConrol.dealPhysicalDamage(target, dmg))
//                            }
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun POISON_ATTACK() = listOf<Component>(
//                    Description(7031, "Poison Attack", "Attacks the target with high chance to poison it.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.ACTIVE, SkillUseType.DAMAGE, EnumSet.of(SkillTargetType.ENEMY))
//                            .onCast { caster, target, skill ->
//
//                                var poison = false
//                                if (GameMath.checkChance(skill.level.value * 7)) {
//                                    target.charConrol.addStatusEffect(StatusEffectEntity(listOf<Component>(
//                                            // TODO: what do we assign statuses?
//                                            Description(10000, "Poison", "...", "effects/attr_up.png"),
//                                            StatusEffectDataComponent(Status.POISONED, 10.0)
//                                    )))
//                                    poison = true
//                                }
//
//                                // TODO: somehow notify user that target is poisoned
//
//                                SkillUseResult(caster.charConrol.attack(target))
//                            }
//                            .withMana(35)
//                            .withCooldown(15.0)
//            )
//
//            fun WEAPON_MASTERY() = listOf<Component>(
//                    Description(7032, "Weapon Mastery", "Passively increases ATK.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .onLearn { caster, skill ->
//                                skill.data.onCast(caster, caster, skill)
//
//                                skill.level.addListener({o, old, new ->
//                                    skill.data.onCast(caster, caster, skill)
//                                })
//                            }
//                            .onCast { caster, target, skill ->
//                                val factor = 7
//                                val value = skill.level.value * factor
//
//                                caster.stats.addBonusStat(Stat.ARM, -skill.testValue)
//
//                                skill.testValue = value
//
//                                caster.stats.addBonusStat(Stat.ARM, skill.testValue)
//
//                                SkillUseResult.NONE
//                            }
//            )
//
//            fun EXPERIENCED_FIGHTER() = listOf<Component>(
//                    Description(7033, "Experienced Fighter", "Passively increases AGI and DEX.", "skills/ic_skill_bash.png"),
//                    SkillDataComponent(SkillType.PASSIVE, SkillUseType.EFFECT, EnumSet.of(SkillTargetType.SELF))
//                            .onLearn { caster, skill ->
//                                skill.data.onCast(caster, caster, skill)
//
//                                skill.level.addListener({o, old, new ->
//                                    skill.data.onCast(caster, caster, skill)
//                                })
//                            }
//                            .onCast { caster, target, skill ->
//                                val value = skill.level.value * 2
//
//                                caster.attributes.addBonusAttribute(Attribute.AGILITY, -skill.testValue)
//                                caster.attributes.addBonusAttribute(Attribute.DEXTERITY, -skill.testValue)
//
//                                skill.testValue = value
//
//                                caster.attributes.addBonusAttribute(Attribute.AGILITY, skill.testValue)
//                                caster.attributes.addBonusAttribute(Attribute.DEXTERITY, skill.testValue)
//
//                                SkillUseResult.NONE
//                            }
//            )
//        }
//
//        object Rogue {
//            //            public static final int SHAMELESS = 7130;
//            //            public static final int DOUBLE_STRIKE = 7131;
//            //            public static final int TRIPLE_STRIKE = 7132;
//            //            public static final int FIVE_FINGER_DEATH_PUNCH = 7133;
//            //            public static final int CRITICAL_STRIKE = 7134;
//
//            //            public static final String SHAMELESS = "Deals more damage if target's % HP is lower than yours. No cooldown but consumes mana";
//            //            public static final String DOUBLE_STRIKE = "Quickly performs two attacks with a chance to stun the target";
//            //            public static final String TRIPLE_STRIKE = "Quickly performs three attacks. Deals more damage if target is stunned";
//            //            public static final String CRITICAL_STRIKE = "Strikes the target with high chance of crit. Crit damage is greater for this skill";
//            //            public static final String FIVE_FINGER_DEATH_PUNCH = "Deals devastating damage to unarmoured targets";
//        }
//
//        object Ranger {
//            //            public static final int PINPOINT_WEAKNESS = 7230;
//            //            public static final int BULLSEYE = 7231;
//            //            public static final int FAST_REFLEXES = 7232;
//            //            public static final int ENCHANTED_ARROW = 7233;
//            //            public static final int EAGLE_EYE = 7234;
//
//            //            public static final String FAST_REFLEXES = "Increases ASPD for the duration";
//            //            public static final String ENCHANTED_ARROW = "Stuns target. Stun lasts longer for target's with high armor rating";
//            //            public static final String EAGLE_EYE = "Passively increases ATK based on DEX";
//            //            public static final String PINPOINT_WEAKNESS = "Decreases target's defense for the duration";
//            //            public static final String BULLSEYE = "Deals armor ignoring damage to target."
//            //                    + "Target's defense is not ignored. "
//            //                    + "Damage is based on caster's DEX";
//        }
//    }
//
//    ////        addSkill(new Skill(ID.Skill.Gladiator.BLOODLUST, "Bloodlust", Desc.Skill.Gladiator.BLOODLUST, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5844145407908548491L;
//    ////
//    ////            private int value = 0;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addBonusStat(Stat.ATK, -value);
//    ////                // div 0 shouldn't occur
//    ////                value = (int) (10*level * caster.getTotalStat(Stat.MAX_HP) / (caster.getHP() + 1));
//    ////                caster.addBonusStat(Stat.ATK, value);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Gladiator.BASH, "Bash", Desc.Skill.Gladiator.BASH, true, 15.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 2177640389884854474L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 3;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = (1 + (15 + 5*level) / 100.0f) * caster.getTotalStat(Stat.ATK);
//    ////                int d = caster.dealPhysicalDamage(target, dmg);
//    ////                target.addStatusEffect(new StatusEffect(Status.STUNNED, 5.0f));
//    ////
//    ////                useResult = new SkillUseResult(GameMath.normalizeDamage(d) + ",STUNNED");
//    ////            }
//    ////        });
//    ////
//
//    ////
//    ////        addSkill(new Skill(ID.Skill.Gladiator.DOUBLE_EDGE, "Double Edge", Desc.Skill.Gladiator.DOUBLE_EDGE, true, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -5670132035647752285L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = (0.1f + 0.02f * level) * caster.getHP();
//    ////                caster.setHP(Math.round(caster.getHP() - dmg));
//    ////                caster.dealPureDamage(target, 2*dmg);
//    ////
//    ////                useResult = new SkillUseResult(2*dmg + ",PURE");
//    ////            }
//    ////        });
//    ////
//
//    ////
//    ////        addSkill(new Skill(ID.Skill.Crusader.LAST_STAND, "Last Stand", Desc.Skill.Crusader.LAST_STAND, true, 60.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -8176078084748576113L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 2 + level*5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addEffect(new Effect((20.0f), ID.Skill.Crusader.LAST_STAND,
//    ////                        new Rune[] {
//    ////                }, new Essence[] {
//    ////                        new Essence(Stat.ATK, Math.round(caster.getBaseStat(Stat.ATK)))
//    ////                }
//    ////                        ));
//    ////
//    ////                useResult = new SkillUseResult("ATK UP!");
//    ////            }
//    ////
//    ////            @Override
//    ////            public boolean isSelfTarget() {
//    ////                return true;
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Gladiator.SHATTER_ARMOR, "Shatter Armor", Desc.Skill.Gladiator.SHATTER_ARMOR, true, 30.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -4834599835655165707L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 2 + level*5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                target.addEffect(new Effect((20.0f), ID.Skill.Gladiator.SHATTER_ARMOR,
//    ////                        new Rune[] {
//    ////                }, new Essence[] {
//    ////                        new Essence(Stat.ARM, -2*level)
//    ////                }
//    ////                        ));
//    ////
//    ////                useResult = new SkillUseResult("ARM -" + 2*level);
//    ////            }
//    ////        });
//    ////
//
//    ////
//    ////        // MAGE SKILL SET
//    ////
//    ////        addSkill(new Skill(ID.Skill.Mage.AIR_SPEAR, "Air Spear", Desc.Skill.Mage.AIR_SPEAR, true, 9.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 6306777256266732648L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) + level *20;
//    ////                int d = caster.dealMagicalDamage(target, dmg, Element.AIR);
//    ////
//    ////                useResult = new SkillUseResult(d);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Wizard.AMPLIFY_MAGIC, "Amplify Magic", Desc.Skill.Wizard.AMPLIFY_MAGIC, true, 30.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -6423702278665617928L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addEffect(new Effect((15.0f), ID.Skill.Wizard.AMPLIFY_MAGIC,
//    ////                        new Rune[] {},
//    ////                        new Essence[] {
//    ////                        new Essence(Stat.MATK, 10*level)
//    ////                }
//    ////                        ));
//    ////
//    ////                useResult = new SkillUseResult("MATK +" + 10*level);
//    ////            }
//    ////
//    ////            @Override
//    ////            public boolean isSelfTarget() {
//    ////                return true;
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Enchanter.ASTRAL_PROTECTION, "Astral Protection", Desc.Skill.Enchanter.ASTRAL_PROTECTION, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 8691650266711866295L;
//    ////
//    ////            private int value = 0;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addBonusStat(Stat.MDEF, -value);
//    ////                value = level * 2;
//    ////                caster.addBonusStat(Stat.MDEF, value);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Mage.EARTH_BOULDER, "Earth Boulder", Desc.Skill.Mage.EARTH_BOULDER, true, 15.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 1871962939560471153L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) + level *25;
//    ////                int d = caster.dealMagicalDamage(target, dmg, Element.EARTH);
//    ////
//    ////                useResult = new SkillUseResult(d);
//    ////            }
//    ////        });
//    ////
//
//    ////
//    ////        addSkill(new Skill(ID.Skill.Mage.ICE_SHARD, "Ice Shard", Desc.Skill.Mage.ICE_SHARD, true, 9.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5561489415884518543L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) + level *20;
//    ////                int d = caster.dealMagicalDamage(target, dmg, Element.WATER);
//    ////
//    ////                useResult = new SkillUseResult(d);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Wizard.MAGIC_MASTERY, "Magic Mastery", Desc.Skill.Wizard.MAGIC_MASTERY, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 9020149732268399438L;
//    ////
//    ////            private int value = 0;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addBonusAttribute(Attribute.INTELLECT, -value);
//    ////                caster.addBonusAttribute(Attribute.WILLPOWER, -value);
//    ////                value = level * 2;
//    ////                caster.addBonusAttribute(Attribute.INTELLECT, value);
//    ////                caster.addBonusAttribute(Attribute.WILLPOWER, value);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Enchanter.MAGIC_SHIELD, "Magic Shield", Desc.Skill.Enchanter.MAGIC_SHIELD, true, 60.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 7104420977798092420L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addEffect(new Effect((25.0f), ID.Skill.Enchanter.MAGIC_SHIELD,
//    ////                        new Rune[] {},
//    ////                        new Essence[] {
//    ////                        new Essence(Stat.ARM, 5*level)
//    ////                }
//    ////                        ));
//    ////
//    ////                useResult = new SkillUseResult("ARM +" + 5*level);
//    ////            }
//    ////
//    ////            @Override
//    ////            public boolean isSelfTarget() {
//    ////                return true;
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Wizard.MENTAL_STRIKE, "Mental Strike", Desc.Skill.Wizard.MENTAL_STRIKE, true, 20.0f) {
//    ////
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -55046003688618764L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 5;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) * (1 + level*0.1f);
//    ////                caster.dealPureDamage(target, dmg);
//    ////
//    ////                useResult = new SkillUseResult(dmg + ",PURE");
//    ////            }
//    ////        });
//    ////
//    ////        // SCOUT SKILL SET
//    ////
//    ////        addSkill(new Skill(ID.Skill.Rogue.CRITICAL_STRIKE, "Critical Strike", Desc.Skill.Rogue.CRITICAL_STRIKE, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -7584376145233708322L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level * 2;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.ATK) + 15 + 5 * level;
//    ////                caster.addBonusStat(Stat.CRIT_CHANCE, 50 + level * 3);
//    ////                int d = caster.dealPhysicalDamage(target, dmg);
//    ////                caster.addBonusStat(Stat.CRIT_CHANCE, -(50 + level * 3));
//    ////
//    ////                useResult = new SkillUseResult(GameMath.normalizeDamage(d));
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Ranger.PINPOINT_WEAKNESS, "Pinpoint Weakness", Desc.Skill.Ranger.PINPOINT_WEAKNESS, true, 15.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 2458408699758838323L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level * 2;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                target.addEffect(new Effect((10.0f), ID.Skill.Ranger.PINPOINT_WEAKNESS,
//    ////                        new Rune[] {},
//    ////                        new Essence[] {
//    ////                        new Essence(Stat.ARM, -2*level)
//    ////                }
//    ////                        ));
//    ////
//    ////                useResult = new SkillUseResult("ARM -" + 2*level);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Rogue.DOUBLE_STRIKE, "Double Strike", Desc.Skill.Rogue.DOUBLE_STRIKE, true, 8.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5685022402103377679L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level * 2;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                int dmg1 = caster.attack(target);
//    ////                int dmg2 = caster.attack(target);
//    ////                boolean stun = false;
//    ////                if (GameMath.checkChance(level*5)) {
//    ////                    target.addStatusEffect(new StatusEffect(Status.STUNNED, 2.5f));
//    ////                    stun = true;
//    ////                }
//    ////
//    ////                useResult = new SkillUseResult(GameMath.normalizeDamage(dmg1) + "," + GameMath.normalizeDamage(dmg2)
//    ////                        + (stun ? ",STUNNED" : ",X2"));
//    ////            }
//    ////        });
//    ////
//
//    ////
//    ////        addSkill(new Skill(ID.Skill.Rogue.SHAMELESS, "Shameless", Desc.Skill.Rogue.SHAMELESS, true, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 2306928037030551618L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 10;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.ATK);
//    ////                float casterHPFactor = caster.getHP() / caster.getTotalStat(Stat.MAX_HP);
//    ////                float targetHPFactor = target.getHP() / target.getTotalStat(Stat.MAX_HP);
//    ////                if (casterHPFactor > targetHPFactor) {
//    ////                    dmg += level * 0.1f * (casterHPFactor - targetHPFactor) * dmg;
//    ////                }
//    ////
//    ////                int d = caster.dealPhysicalDamage(target, dmg);
//    ////                useResult = new SkillUseResult(GameMath.normalizeDamage(d));
//    ////            }
//    ////        });
//
//    ////
//    ////        addSkill(new Skill(ID.Skill.Rogue.TRIPLE_STRIKE, "Triple Strike", Desc.Skill.Rogue.TRIPLE_STRIKE, true, 40.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 8295208480454374043L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.ATK);
//    ////                if (target.hasStatusEffect(Status.STUNNED)) {
//    ////                    dmg += level * 15;
//    ////                }
//    ////
//    ////                int dmg1 = GameMath.normalizeDamage(caster.dealPhysicalDamage(target, dmg));
//    ////                int dmg2 = GameMath.normalizeDamage(caster.dealPhysicalDamage(target, dmg));
//    ////                int dmg3 = GameMath.normalizeDamage(caster.dealPhysicalDamage(target, dmg));
//    ////
//    ////                useResult = new SkillUseResult(dmg1 + "," + dmg2 + "," + dmg3 + ",X3");
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Crusader.HOLY_LIGHT, "Holy Light", Desc.Skill.Crusader.HOLY_LIGHT, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5685022402103377679L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                target.setHP(Math.min(target.getHP() + 30 + level*10, (int)target.getTotalStat(Stat.MAX_HP)));
//    ////                target.addEffect(new Effect(20.0f, ID.Skill.Crusader.HOLY_LIGHT, new Rune[] {
//    ////                        new Rune(Attribute.VITALITY, level*2)
//    ////                },
//    ////                new Essence[] {}));
//    ////
//    ////                useResult = new SkillUseResult("VIT +" + level*2);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Crusader.PRECISION_STRIKE, "Precision Strike", Desc.Skill.Crusader.PRECISION_STRIKE, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 2024648263069876L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.ATK) + (caster.getTotalAttribute(Attribute.STRENGTH) / 10) * level;
//    ////                caster.dealPureDamage(target, dmg);
//    ////
//    ////                useResult = new SkillUseResult((int)dmg);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Crusader.DIVINE_ARMOR, "Divine Armor", Desc.Skill.Crusader.DIVINE_ARMOR, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -7936080589333242098L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                // impl is in ARMOR_MASTERY
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Crusader.FAITH, "Faith", Desc.Skill.Crusader.FAITH, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 4325213967370795918L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                // impl is in WARRIOR_HEART
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Gladiator.ENDURANCE, "Endurance", Desc.Skill.Gladiator.ENDURANCE, true, 40.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -7936080589333242098L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addEffect(new Effect(15.0f, ID.Skill.Gladiator.ENDURANCE, new Rune[] {},
//    ////                        new Essence[] {
//    ////                        new Essence(Stat.DEF, 2*level),
//    ////                        new Essence(Stat.HP_REGEN, 2*level)
//    ////                }));
//    ////
//    ////                useResult = new SkillUseResult("DEF +" + level*2 + ", HP REGEN +" + 2*level);
//    ////            }
//    ////
//    ////            @Override
//    ////            public boolean isSelfTarget() {
//    ////                return true;
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Wizard.THUNDERBOLT_FIRESTORM, "Thunderbolt Firestorm", Desc.Skill.Wizard.THUNDERBOLT_FIRESTORM, true, 40.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 4325213967370795918L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) * (1.0f + level*0.15f);
//    ////                int dmg1 = caster.dealMagicalDamage(target, dmg, Element.FIRE);
//    ////                int dmg2 = caster.dealMagicalDamage(target, dmg, Element.AIR);
//    ////
//    ////                useResult = new SkillUseResult(dmg1 + "," + dmg2);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Wizard.ICICLE_AVALANCHE, "Icicle Avalanche", Desc.Skill.Wizard.ICICLE_AVALANCHE, true, 40.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 6791451275759000638L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = caster.getTotalStat(Stat.MATK) * (1.0f + level*0.15f);
//    ////                int dmg1 = caster.dealMagicalDamage(target, dmg, Element.WATER);
//    ////                int dmg2 = caster.dealMagicalDamage(target, dmg, Element.EARTH);
//    ////
//    ////                useResult = new SkillUseResult(dmg1 + "," + dmg2);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Enchanter.MANA_BURN, "Mana Burn", Desc.Skill.Enchanter.MANA_BURN, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 1031700846462374399L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                int oldSP = target.getSP();
//    ////                target.setSP(Math.max(oldSP - 50 * level, 0));
//    ////                int dmg = caster.dealMagicalDamage(target, oldSP-target.getSP(), Element.NEUTRAL);
//    ////
//    ////                useResult = new SkillUseResult(dmg);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Enchanter.CURSE_OF_WITCHCRAFT, "Curse of Witchcraft", Desc.Skill.Enchanter.CURSE_OF_WITCHCRAFT, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 8295208480454374043L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                target.addStatusEffect(new StatusEffect(Status.SILENCED, level*3));
//    ////
//    ////                useResult = new SkillUseResult("SILENCED");
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Enchanter.MIND_BLAST, "Mind Blast", Desc.Skill.Enchanter.MIND_BLAST, true, 20.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = -3587620067204007562L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                // TODO: impl
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Rogue.FIVE_FINGER_DEATH_PUNCH, "Five Finger Death Punch", Desc.Skill.Rogue.FIVE_FINGER_DEATH_PUNCH, true, 35.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 9128637084476710269L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = 20 + level*30 - target.getTotalStat(Stat.ARM);
//    ////                int d = caster.dealPhysicalDamage(target, dmg);
//    ////
//    ////                useResult = new SkillUseResult(GameMath.normalizeDamage(d));
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Ranger.BULLSEYE, "Bullseye", Desc.Skill.Ranger.BULLSEYE, true, 60.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 3498114139079315L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 5 + level * 10;
//    ////            }
//    ////
//    ////            @Override
//    ////            public void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float dmg = 100 + 0.2f*level * caster.getTotalAttribute(Attribute.DEXTERITY) - target.getTotalStat(GameCharacter.DEF);
//    ////                caster.dealPureDamage(target, dmg);
//    ////
//    ////                useResult = new SkillUseResult(dmg + ",PURE");
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Ranger.EAGLE_EYE, "Eagle Eye", Desc.Skill.Ranger.EAGLE_EYE, false, 0.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 7005439875094828368L;
//    ////
//    ////            private int value = 0;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 0;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addBonusStat(Stat.ATK, -value);
//    ////                value = (int)(caster.getTotalAttribute(Attribute.DEXTERITY) * level * 0.1f);
//    ////                caster.addBonusStat(Stat.ATK, value);
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Ranger.ENCHANTED_ARROW, "Enchanted Arrows", Desc.Skill.Ranger.ENCHANTED_ARROW, true, 35.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5416340917264724397L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                float duration = target.getTotalStat(Stat.ARM) * 0.1f;
//    ////                target.addStatusEffect(new StatusEffect(Status.STUNNED, duration));
//    ////
//    ////                useResult = new SkillUseResult("STUNNED");
//    ////            }
//    ////        });
//    ////
//    ////        addSkill(new Skill(ID.Skill.Ranger.FAST_REFLEXES, "Fast Reflexes", Desc.Skill.Ranger.FAST_REFLEXES, true, 35.0f) {
//    ////            /**
//    ////             *
//    ////             */
//    ////            private static final long serialVersionUID = 5766544471206156505L;
//    ////
//    ////            @Override
//    ////            public int getManaCost() {
//    ////                return 3 + level*4;
//    ////            }
//    ////
//    ////            @Override
//    ////            protected void useImpl(GameCharacter caster, GameCharacter target) {
//    ////                caster.addEffect(new Effect(10.0f, ID.Skill.Ranger.FAST_REFLEXES, new Rune[] {},
//    ////                        new Essence[] {
//    ////                        new Essence(Stat.ASPD, level*2)
//    ////                }));
//    ////
//    ////                useResult = new SkillUseResult("ASPD +" + level*2);
//    ////            }
//    ////
//    ////            @Override
//    ////            public boolean isSelfTarget() {
//    ////                return true;
//    ////            }
//    ////        });
//    ////
//    ////
//    ////        // ENEMIES
//    //
//    //        EnemyBuilder enemyBuilder = new EnemyBuilder();
//    //        enemyBuilder.id(ID.Enemy.MINOR_EARTH_SPIRIT)
//    //                    .description(Desc.Enemy.MINOR_EARTH_SPIRIT)
//    //                    .name("Minor Earth Spirit")
//    //                    .textureName("enemy.png")
//    //                    .xp(new Experience(100, 100, 100))
//    //                    .element(Element.EARTH)
//    //                    .drops(new DroppableItem(ID.Weapon.KNIFE, 50), new DroppableItem(ID.Armor.CHAINMAL, 35));
//    //
//    //        addEnemy(enemyBuilder);
//    //
//    ////
//    ////        addEnemy(new Enemy(ID.Enemy.MINOR_FIRE_SPIRIT, "Minor Fire Spirit", Desc.Enemy.MINOR_FIRE_SPIRIT,
//    ////                EnemyType.NORMAL, Element.FIRE, 1, new AttributeInfo(),
//    ////                new Experience(100, 100, 100), 0, new DroppableItem(ID.Weapon.KNIFE, 50), new DroppableItem(ID.Armor.THANATOS_BODY_ARMOR, 10)));
//    ////
//    ////        addEnemy(new Enemy(ID.Enemy.MINOR_EARTH_SPIRIT, "Minor Earth Spirit", Desc.Enemy.MINOR_EARTH_SPIRIT,
//    ////                EnemyType.NORMAL, Element.EARTH, 1, new AttributeInfo(),
//    ////                new Experience(100, 100, 100), 0, new DroppableItem(ID.Weapon.IRON_SWORD, 15), new DroppableItem(ID.Armor.SAPPHIRE_LEGION_PLATE_MAIL, 5)));
//    ////
//    ////        addEnemy(new Enemy(ID.Enemy.MINOR_WATER_SPIRIT, "Minor Water Spirit", Desc.Enemy.MINOR_WATER_SPIRIT,
//    ////                EnemyType.NORMAL, Element.WATER, 1, new AttributeInfo(),
//    ////                new Experience(100, 100, 100), 0, new DroppableItem(ID.Armor.CHAINMAL, 25), new DroppableItem(ID.Weapon.SOUL_REAPER, 5)));
//    //    }
}