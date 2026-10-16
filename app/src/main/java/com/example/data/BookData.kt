package com.example.data

data class PromptItem(
    val id: String,
    val globalNumber: Int,
    val pageNumber: Int,
    val category: String,
    val title: String,
    val type: String, // "IMAGE PROMPT" or "VIDEO PROMPT"
    val text: String
)

data class CategoryMeta(
    val id: String,
    val name: String,
    val colorHex: Long,
    val startPage: Int,
    val endPage: Int,
    val promptCount: Int,
    val description: String
)

sealed class PageType {
    object Cover : PageType()
    object HowToAndToc1 : PageType()
    object Toc2 : PageType()
    data class Prompts(val category: String) : PageType()
    object Checklist : PageType()
}

data class BookPage(
    val pageNumber: Int,
    val title: String,
    val category: String,
    val pageType: PageType,
    val prompts: List<PromptItem> = emptyList()
)

object BookRepository {
    val categories: List<CategoryMeta> = listOf(
        CategoryMeta(
            id = "miniature",
            name = "Miniature Worlds",
            colorHex = 0xFF06D6A0,
            startPage = 4,
            endPage = 5,
            promptCount = 10,
            description = "Intricate dioramas, tiny handcrafted cities, and tilt-shift macro realism."
        ),
        CategoryMeta(
            id = "architecture",
            name = "Architecture & Interiors",
            colorHex = 0xFFF77F00,
            startPage = 6,
            endPage = 7,
            promptCount = 10,
            description = "Futuristic eco-homes, luxury desert villas, and serene spatial design."
        ),
        CategoryMeta(
            id = "portraits",
            name = "Cinematic Portraits",
            colorHex = 0xFFE63946,
            startPage = 8,
            endPage = 9,
            promptCount = 10,
            description = "Rain-soaked heroes, golden-hour travelers, and dramatic studio lighting."
        ),
        CategoryMeta(
            id = "nature",
            name = "Nature & Wildlife",
            colorHex = 0xFF2A9D8F,
            startPage = 10,
            endPage = 11,
            promptCount = 10,
            description = "High-speed bird dives, elusive snow leopards, and delicate rainforest macros."
        ),
        CategoryMeta(
            id = "scifi",
            name = "Futuristic & Sci-Fi",
            colorHex = 0xFF00B4D8,
            startPage = 12,
            endPage = 13,
            promptCount = 10,
            description = "Orbital station windows, Mars outposts, and glowing cyberpunk night markets."
        ),
        CategoryMeta(
            id = "food_product",
            name = "Food & Product Photography",
            colorHex = 0xFFE76F51,
            startPage = 14,
            endPage = 15,
            promptCount = 10,
            description = "Gourmet dishes, artisan latte art, luxury timepieces, and cosmetic splashes."
        ),
        CategoryMeta(
            id = "fantasy",
            name = "Fantasy & Surreal",
            colorHex = 0xFF9D4EDD,
            startPage = 16,
            endPage = 17,
            promptCount = 10,
            description = "Floating island waterfalls, clockwork gardens, and majestic cloud whales."
        ),
        CategoryMeta(
            id = "travel",
            name = "Travel & Culture",
            colorHex = 0xFFE09F3E,
            startPage = 18,
            endPage = 19,
            promptCount = 10,
            description = "South Asian bazaars at dawn, mountain pass jeeps, and cliffside monasteries."
        ),
        CategoryMeta(
            id = "tech",
            name = "Technology & Gadgets",
            colorHex = 0xFF4361EE,
            startPage = 20,
            endPage = 21,
            promptCount = 10,
            description = "Transparent headphones, foldable phones, and desktop creator workstations."
        ),
        CategoryMeta(
            id = "vehicles",
            name = "Vehicles & Motion",
            colorHex = 0xFFD90429,
            startPage = 22,
            endPage = 23,
            promptCount = 9,
            description = "Electric supercars at night, vintage steam trains, and cruising aviation."
        ),
        CategoryMeta(
            id = "creator",
            name = "Social Media & Creator",
            colorHex = 0xFFF72585,
            startPage = 24,
            endPage = 25,
            promptCount = 10,
            description = "Vertical video sets, podcast studios, editing workstations, and reel setups."
        ),
        CategoryMeta(
            id = "motion",
            name = "Motion & Video Prompts",
            colorHex = 0xFF4CC9F0,
            startPage = 26,
            endPage = 27,
            promptCount = 10,
            description = "Dynamic video generation prompts with shot progression and camera movement."
        )
    )

    val allPrompts: List<PromptItem> = listOf(
        // Page 4: Miniature Worlds 1-6
        PromptItem(
            id = "p1",
            globalNumber = 1,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Miniature London Workshop",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic cinematic vertical 9:16 photograph of a skilled young miniature artist in a professional workshop, carefully painting a handcrafted miniature London diorama. Include Big Ben, Westminster, Tower Bridge, red double-decker buses, black cabs, red telephone booths, cobblestone streets, tiny people, warm miniature streetlights, and a black display base. Show the artist's focused face, both naturally positioned hands, precision brushes, tweezers, paint jars, and architectural blueprints. Use a three-quarter macro camera angle, physically accurate scale, realistic materials, shallow depth of field, warm cinematic lighting, HDR, and crisp 8K-style detail. The city must clearly look like a physical handcrafted model. No cartoon style, distorted architecture, extra fingers, duplicated objects, text overlays, or watermark."
        ),
        PromptItem(
            id = "p2",
            globalNumber = 2,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Tiny Japanese Kyoto Street",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 macro documentary image of an artisan building a miniature Kyoto street on a wooden workbench. Include tiny machiya townhouses, a small red torii gate, stone paving, miniature cherry trees, lanterns, bicycles, and tiny pedestrians. Show realistic hands placing a roof tile with tweezers, paint pots and craft tools nearby. Soft dusk lighting, amber lantern glow, delicate material textures, accurate scale, cinematic depth of field, highly detailed handcrafted model, no illustration, no text, no watermark."
        ),
        PromptItem(
            id = "p3",
            globalNumber = 3,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Miniature Venice Canal",
            type = "IMAGE PROMPT",
            text = "Generate an ultra-realistic vertical 9:16 photograph of a miniature Venice canal diorama being assembled by an artist. Feature tiny gondolas, arched stone bridges, pastel waterfront buildings, miniature laundry lines, windows glowing at sunset, and gently rippled resin water. Show a focused model maker applying fine details with a precision brush. Macro lens, realistic water reflections, handcrafted textures, warm/cool cinematic contrast, consistent scale, no full-size-looking structures, no cartoon effects, no text or watermark."
        ),
        PromptItem(
            id = "p4",
            globalNumber = 4,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Miniature Mountain Village",
            type = "IMAGE PROMPT",
            text = "Create a highly realistic 9:16 image of a handcrafted miniature South Asian mountain village on a workbench. Include stone-and-timber houses, narrow footpaths, terraced fields, tiny goats, a stream, stacked firewood, and snow-dusted peaks in the backdrop of the model. An artisan adds tiny roof details with a brush. Natural window light, macro photography, authentic materials, believable scale, fine terrain textures, cinematic color grading, no distorted buildings, no text or watermark."
        ),
        PromptItem(
            id = "p5",
            globalNumber = 5,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Tiny Spaceport",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 image of a miniature futuristic spaceport model under construction. Include tiny launch towers, spacecraft, service vehicles, illuminated landing pads, cables, and small worker figurines. Show an engineer-modeler painting a tiny spacecraft at a detailed hobby bench. Cool blue practical lights and warm task lighting, macro lens, realistic metal and resin, precise scale, documentary realism, no fantasy illustration, no illegible labels, no watermark."
        ),
        PromptItem(
            id = "p6",
            globalNumber = 6,
            pageNumber = 4,
            category = "Miniature Worlds",
            title = "Miniature Ancient Rome",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 macro photograph of a handcrafted ancient Rome diorama. Feature a miniature Colosseum, stone forum, columns, market stalls, tiny carts, cypress trees, and small human figures. A model artist carefully dry-brushes weathered stone textures. Warm late-afternoon light, realistic stone dust, accurate scale, shallow depth of field, fine craftsmanship, no modern objects, no cartoon look, no text or watermark."
        ),

        // Page 5: Miniature Worlds 7-10
        PromptItem(
            id = "p7",
            globalNumber = 7,
            pageNumber = 5,
            category = "Miniature Worlds",
            title = "Miniature Desert Caravan",
            type = "IMAGE PROMPT",
            text = "Generate an ultra-realistic vertical 9:16 photograph of a miniature desert caravan diorama being crafted. Include tiny camels, fabric tents, palm trees, a market path, clay jars, rugs, and golden dunes with realistic sand grain. Show a maker placing a miniature tent rope with tweezers. Warm sunset lighting, macro detail, believable scale, physical model materials, cinematic shadows, no oversized objects, no text or watermark."
        ),
        PromptItem(
            id = "p8",
            globalNumber = 8,
            pageNumber = 5,
            category = "Miniature Worlds",
            title = "Miniature Rainy City",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 miniature modern city scene on a workbench after rain. Include tiny glass towers, puddles, umbrellas, taxis, traffic lights, wet asphalt, and glowing shop windows. Show an artist adding a tiny reflective puddle using a dropper. Macro documentary photography, realistic reflections, scale-appropriate droplets, moody teal-and-amber lighting, no real-scale city illusion, no cartoon style, no text or watermark."
        ),
        PromptItem(
            id = "p9",
            globalNumber = 9,
            pageNumber = 5,
            category = "Miniature Worlds",
            title = "Miniature Railway Station",
            type = "IMAGE PROMPT",
            text = "Create a detailed 9:16 photograph of a handcrafted miniature railway station. Include a vintage locomotive, platform benches, tiny passengers, signal lights, a station clock, luggage carts, and tracks with realistic ballast. Show a model maker aligning rails with precision tools. Warm station lights, cinematic macro focus, accurate proportions, metal and wood textures, no duplicated trains, no text overlays, no watermark."
        ),
        PromptItem(
            id = "p10",
            globalNumber = 10,
            pageNumber = 5,
            category = "Miniature Worlds",
            title = "Miniature Farm Diorama",
            type = "IMAGE PROMPT",
            text = "Generate a realistic vertical 9:16 macro photograph of a miniature farm diorama. Include a red barn, crop rows, hay bales, tiny tractor, chickens, sheep, a pond, and miniature farmers. Show an artisan painting tiny fence rails. Soft morning light, lifelike vegetation, believable model scale, detailed soil and wood grain, documentary realism, no cartoon look, no text or watermark."
        ),

        // Page 6: Architecture & Interiors 1-7
        PromptItem(
            id = "p11",
            globalNumber = 11,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Futuristic Eco House",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 architectural visualization of a futuristic eco-home built into a green hillside. Show curved timber, glass walls, a planted roof, solar panels, rainwater channels, and warm interior lighting. Include a person on the terrace for scale, lush native plants, realistic shadows, refined material details, and a premium architecture-magazine look. No impossible structural connections, no text, no watermark."
        ),
        PromptItem(
            id = "p12",
            globalNumber = 12,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Luxury Desert Villa",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 architectural photograph of a contemporary luxury villa in a desert landscape. Use pale limestone, sculptural arches, shaded courtyards, an infinity pool reflecting the sunset, native grasses, and warm interior glow. Capture a wide three-quarter angle, natural stone textures, realistic water, physically accurate light, premium editorial quality, no exaggerated proportions, no text or watermark."
        ),
        PromptItem(
            id = "p13",
            globalNumber = 13,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Tiny Apartment Makeover",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic vertical 9:16 before-and-after style interior image of a compact apartment transformed with clever storage, a fold-away desk, warm oak cabinetry, neutral textiles, and layered lighting. Show the finished space as one coherent room, with believable furniture dimensions and clear walking paths. Architectural photography, natural window light, crisp textures, no split-screen unless requested, no text, no watermark."
        ),
        PromptItem(
            id = "p14",
            globalNumber = 14,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Floating Ocean House",
            type = "IMAGE PROMPT",
            text = "Create a realistic 9:16 architectural image of a modern floating house on calm turquoise water. Include timber decking, glass railings, solar roof panels, subtle mooring details, and a small boat nearby. Show realistic reflections, a hazy coastal horizon, and warm early-morning light. The building must appear structurally plausible, with accurate scale and no fantasy distortions, no text or watermark."
        ),
        PromptItem(
            id = "p15",
            globalNumber = 15,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Mountain Glass Cabin",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 photograph of a glass-fronted timber cabin in a dramatic mountain forest. Snow rests on the roof, warm firelight glows inside, and pine trees frame the foreground. Capture realistic glass reflections, detailed wood grain, soft snowfall, and natural twilight color. Premium travel-magazine photography, no impossible architecture, no text or watermark."
        ),
        PromptItem(
            id = "p16",
            globalNumber = 16,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Modern Mosque Courtyard",
            type = "IMAGE PROMPT",
            text = "Create a respectful photorealistic 9:16 architectural photograph of a contemporary mosque courtyard with elegant geometric screens, pale stone, a reflecting pool, and soft sunlight filtering through arches. Keep the design serene and structurally realistic, with subtle human figures for scale and immaculate material detail. No people posed disrespectfully, no text, no watermark."
        ),
        PromptItem(
            id = "p17",
            globalNumber = 17,
            pageNumber = 6,
            category = "Architecture & Interiors",
            title = "Minimalist Creative Studio",
            type = "IMAGE PROMPT",
            text = "Create a realistic vertical 9:16 photograph of a creator's minimalist studio with a large desk, camera gear, softbox lights, a powerful computer, acoustic panels, plants, and neatly arranged props. Use warm practical lighting and natural window fill, balanced composition, realistic cable management, premium interior photography, no visible brand logos, no text, no watermark."
        ),

        // Page 7: Architecture & Interiors 8-10
        PromptItem(
            id = "p18",
            globalNumber = 18,
            pageNumber = 7,
            category = "Architecture & Interiors",
            title = "Underwater Restaurant",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 interior photograph of a plausible underwater restaurant with a curved acrylic viewing wall, diners in the distance, reef life outside, warm pendants, and dark polished timber. Show realistic water refraction and believable construction details. Elegant luxury hospitality aesthetic, no fantasy creatures, no distorted fish, no text or watermark."
        ),
        PromptItem(
            id = "p19",
            globalNumber = 19,
            pageNumber = 7,
            category = "Architecture & Interiors",
            title = "Rooftop Garden",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 photograph of a modern rooftop garden with raised planters, herbs, a small dining nook, warm string lights, city skyline, and a pergola. Show realistic leaf textures, practical irrigation, safe railings, and dusk ambience. Architectural editorial style, no text, no watermark."
        ),
        PromptItem(
            id = "p20",
            globalNumber = 20,
            pageNumber = 7,
            category = "Architecture & Interiors",
            title = "Smart Home Living Room",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 living room featuring subtle smart-home technology, concealed speakers, indirect lighting, a large window, textured neutral furniture, and a discreet wall control panel. Keep the space welcoming, practical, and physically believable. Natural shadows, premium interior photography, no futuristic holograms, no visible logos, no text or watermark."
        ),

        // Page 8: Cinematic Portraits 1-7
        PromptItem(
            id = "p21",
            globalNumber = 21,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Rain-Soaked Film Hero",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 cinematic portrait of a fictional young man standing in gentle rain on a neon-lit city street at night. Wet hair, realistic water droplets, dark tailored jacket, thoughtful expression, reflections on pavement, red and cyan practical lights, shallow depth of field, natural skin texture, 85mm portrait lens look. Keep facial anatomy realistic; no plastic skin, no text, no watermark."
        ),
        PromptItem(
            id = "p22",
            globalNumber = 22,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Golden-Hour Traveler",
            type = "IMAGE PROMPT",
            text = "Generate a premium 9:16 travel portrait of a fictional traveler on a windswept coastal cliff at golden hour. Use natural clothing movement, warm rim light, distant sea haze, realistic skin texture, and an authentic candid expression. Frame from waist up with the horizon balanced behind the subject. Editorial photography, no over-retouching, no text or watermark."
        ),
        PromptItem(
            id = "p23",
            globalNumber = 23,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Traditional Mountain Elder",
            type = "IMAGE PROMPT",
            text = "Create a respectful ultra-realistic vertical 9:16 portrait of a fictional elderly mountain villager wearing traditional regional clothing and a textured wool cap. Place him outside a stone-and-timber home, with weathered hands, gentle expression, natural skin details, and soft overcast light. Documentary photography, culturally plausible clothing, no caricature, no exaggerated age effects, no text or watermark."
        ),
        PromptItem(
            id = "p24",
            globalNumber = 24,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Fashion Editorial in Monochrome",
            type = "IMAGE PROMPT",
            text = "Create a high-end black-and-white vertical 9:16 fashion portrait of a fictional model in a structured dark coat against a simple textured studio wall. Use sculpted side lighting, authentic fabric weave, subtle film grain, strong but natural expression, and precise focus on the eyes. No beauty-filter look, no text, no watermark."
        ),
        PromptItem(
            id = "p25",
            globalNumber = 25,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Cyberpunk Detective",
            type = "IMAGE PROMPT",
            text = "Create a realistic 9:16 cinematic portrait of a fictional detective in a rain-drenched futuristic alley. Practical neon signage without readable words, reflective coat, atmospheric steam, realistic skin, moody blue and amber lighting, and a shallow-focus background. Keep technology grounded and believable, no extra limbs, no text overlays, no watermark."
        ),
        PromptItem(
            id = "p26",
            globalNumber = 26,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Desert Nomad Portrait",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 portrait of a fictional desert nomad wearing layered sand-colored garments and a wrapped headscarf, standing among wind-shaped dunes at sunrise. Show natural fabric texture, realistic sunlit skin, windblown sand, and an unposed expression. Respectful documentary aesthetic, no stereotypes, no text or watermark."
        ),
        PromptItem(
            id = "p27",
            globalNumber = 27,
            pageNumber = 8,
            category = "Cinematic Portraits",
            title = "Chef at Work",
            type = "IMAGE PROMPT",
            text = "Generate an ultra-realistic 9:16 editorial portrait of a professional chef finishing a dish in a busy restaurant kitchen. Show clean, naturally posed hands, steam from a pan, stainless steel surfaces, focused expression, and warm practical lighting. Capture the chef and plated food in one coherent frame, documentary realism, no extra fingers, no logos, no text or watermark."
        ),

        // Page 9: Cinematic Portraits 8-10
        PromptItem(
            id = "p28",
            globalNumber = 28,
            pageNumber = 9,
            category = "Cinematic Portraits",
            title = "Street Photographer",
            type = "IMAGE PROMPT",
            text = "Create a cinematic vertical 9:16 portrait of a fictional street photographer holding a camera on a busy old-city street at dawn. Soft fog, authentic layered clothing, natural candid pose, realistic camera details, and blurred pedestrians in the background. Use subtle film grain and true-to-life skin, no brand logos, no text, no watermark."
        ),
        PromptItem(
            id = "p29",
            globalNumber = 29,
            pageNumber = 9,
            category = "Cinematic Portraits",
            title = "Studio Musician",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 portrait of a fictional musician in a dim recording studio, wearing understated modern clothing and headphones around the neck. Include a microphone, acoustic panels, a mixing console, and warm indicator lights. Cinematic low-key lighting, realistic materials, natural hands and face, no readable labels, no text or watermark."
        ),
        PromptItem(
            id = "p30",
            globalNumber = 30,
            pageNumber = 9,
            category = "Cinematic Portraits",
            title = "Rainy Window Reflection",
            type = "IMAGE PROMPT",
            text = "Create a poetic ultra-realistic vertical 9:16 portrait of a fictional person standing beside a rain-covered window, with city lights softly reflected in the glass. Use natural skin texture, thoughtful expression, muted colors, soft side light, and delicate water trails. Premium film-still composition, no double-face distortions, no text or watermark."
        ),

        // Page 10: Nature & Wildlife 1-8
        PromptItem(
            id = "p31",
            globalNumber = 31,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Kingfisher Dive",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic wildlife photograph in vertical 9:16 format of a kingfisher diving toward a clear stream. Freeze the moment with wings spread, tiny droplets suspended in air, accurate feather detail, and natural habitat vegetation. Use a telephoto wildlife-photography look, fast-shutter clarity, anatomically correct bird, no illustration, no text or watermark."
        ),
        PromptItem(
            id = "p32",
            globalNumber = 32,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Snow Leopard on Ridge",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 wildlife image of a snow leopard standing on a rocky Himalayan ridge at dawn. Show accurate anatomy, thick spotted fur, powerful paws, distant snow-covered peaks, and crisp cold air. Natural telephoto composition, subtle golden light, conservation-documentary realism, no fantasy glow, no text or watermark."
        ),
        PromptItem(
            id = "p33",
            globalNumber = 33,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Fox in Autumn Forest",
            type = "IMAGE PROMPT",
            text = "Create a cinematic vertical 9:16 wildlife photograph of a red fox walking through fallen autumn leaves in a quiet forest. Capture realistic fur, alert eyes, natural paw placement, soft backlight, and a blurred woodland background. True-to-life proportions and color, no cartoon expression, no text or watermark."
        ),
        PromptItem(
            id = "p34",
            globalNumber = 34,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Elephant at Waterhole",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 wildlife documentary image of an elephant drinking at a natural waterhole at sunrise. Show realistic skin folds, wet trunk, reflections, muddy ground, and birds in the distance. Use warm natural light and a long-lens look. Accurate anatomy, respectful wildlife behavior, no text or watermark."
        ),
        PromptItem(
            id = "p35",
            globalNumber = 35,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Hummingbird and Flower",
            type = "IMAGE PROMPT",
            text = "Generate a macro wildlife photograph in vertical 9:16 format of a hummingbird hovering beside a vivid flower. Show iridescent feathers, transparent wing motion, delicate pollen, and a soft garden background. Use realistic natural light and fine feather detail, no oversaturated fantasy colors, no extra wings, no text or watermark."
        ),
        PromptItem(
            id = "p36",
            globalNumber = 36,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Misty Waterfall",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 landscape photograph of a tall waterfall in a lush mountain forest. Include mossy rocks, fine mist, ferns, and a clear pool. Use long-exposure water texture with sharp surrounding foliage, believable atmospheric depth, natural greens, and cinematic composition. No fantasy effects, no text or watermark."
        ),
        PromptItem(
            id = "p37",
            globalNumber = 37,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Desert Bloom",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 close landscape image of desert wildflowers blooming after rare rainfall. Include delicate petals, damp sand, distant dunes, tiny insects, and soft sunrise light. Macro foreground with natural depth, scientifically plausible plants, realistic colors, no text or watermark."
        ),
        PromptItem(
            id = "p38",
            globalNumber = 38,
            pageNumber = 10,
            category = "Nature & Wildlife",
            title = "Ocean Turtle",
            type = "IMAGE PROMPT",
            text = "Generate a realistic vertical 9:16 underwater wildlife photograph of a sea turtle gliding above a coral reef. Show accurate shell pattern, natural flipper anatomy, suspended particles, sun rays through water, and diverse but plausible reef life. Documentary underwater photography, no fantasy creatures, no text or watermark."
        ),

        // Page 11: Nature & Wildlife 9-10
        PromptItem(
            id = "p39",
            globalNumber = 39,
            pageNumber = 11,
            category = "Nature & Wildlife",
            title = "Owl in Snow",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic 9:16 wildlife photograph of a snowy owl perched on a frost-covered branch during light snowfall. Detailed feathers, alert eyes, accurate talons, natural winter light, soft background bokeh, and a quiet winter atmosphere. No human-like expression, no illustration, no text or watermark."
        ),
        PromptItem(
            id = "p40",
            globalNumber = 40,
            pageNumber = 11,
            category = "Nature & Wildlife",
            title = "Rainforest Macro Frog",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 macro image of a small rainforest frog resting on a wet leaf. Show realistic skin texture, tiny water droplets, subtle leaf veins, and a blurred green forest background. Accurate amphibian anatomy, natural colors, macro lens, no exaggerated eyes, no text or watermark."
        ),

        // Page 12: Futuristic & Sci-Fi 1-8
        PromptItem(
            id = "p41",
            globalNumber = 41,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Orbital City Window",
            type = "IMAGE PROMPT",
            text = "Create a cinematic vertical 9:16 science-fiction image of an astronaut looking through a panoramic station window at a vast orbital city and the curve of Earth. Use plausible spacecraft materials, realistic reflections on the visor, practical interior lights, and immense scale. Photorealistic VFX still, no impossible anatomy, no text or watermark."
        ),
        PromptItem(
            id = "p42",
            globalNumber = 42,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Mars Research Base",
            type = "IMAGE PROMPT",
            text = "Generate an ultra-realistic 9:16 concept photograph of a modular research base on Mars. Include pressurized habitat modules, dust-covered solar panels, a rover, airlocks, and distant red cliffs. Show realistic engineering, fine dust, low-angle sunlight, and documentary-style science imagery. No fantasy spacecraft, no text or watermark."
        ),
        PromptItem(
            id = "p43",
            globalNumber = 43,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Robotic Greenhouse",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 image of a futuristic greenhouse where small agricultural robots tend vertical crops under carefully balanced grow lights. Include condensation on glass, real leaves, irrigation lines, and a technician monitoring equipment. Plausible near-future technology, detailed surfaces, cinematic realism, no holographic clutter, no text or watermark."
        ),
        PromptItem(
            id = "p44",
            globalNumber = 44,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Neon Night Market",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 photograph of a fictional futuristic night market with narrow lanes, steam rising from food stalls, wet pavement, overhead cables, and diverse shoppers. Use practical neon lighting and realistic food textures. Keep signs abstract or unreadable, with believable urban density and natural human anatomy, no text overlays, no watermark."
        ),
        PromptItem(
            id = "p45",
            globalNumber = 45,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Underwater Research Lab",
            type = "IMAGE PROMPT",
            text = "Generate a realistic vertical 9:16 image of a deep-sea research habitat with scientists observing marine life through thick pressure-rated windows. Include instruments, cables, dim blue ambient light, realistic water distortion, and plausible construction. Premium cinematic science photography, no fantasy monsters, no text or watermark."
        ),
        PromptItem(
            id = "p46",
            globalNumber = 46,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Solar-Powered Flying Taxi",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 near-future city image of a compact electric air taxi landing on a rooftop pad. Show credible rotor or ducted-fan design, safety markings without readable text, passengers at a distance, glass towers, and morning haze. Physically plausible scale and lighting, no impossible flight geometry, no text or watermark."
        ),
        PromptItem(
            id = "p47",
            globalNumber = 47,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "AI Lab of Tomorrow",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 image of a modern research lab with robotic arms, glass partitions, computing racks, and scientists collaborating around a workbench. Cool task lighting, tidy realistic cables, accurate equipment proportions, subtle reflections, and an editorial technology-photography look. No holographic clichés, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p48",
            globalNumber = 48,
            pageNumber = 12,
            category = "Futuristic & Sci-Fi",
            title = "Lunar Greenhouse Dome",
            type = "IMAGE PROMPT",
            text = "Create a realistic cinematic 9:16 image of a greenhouse dome on the Moon, with crops growing under artificial sunlight and astronauts tending plants. Show lunar dust beyond the transparent structure, a black sky, plausible pressure-shell engineering, and realistic shadows. Science-based speculative design, no fantasy glow, no text or watermark."
        ),

        // Page 13: Futuristic & Sci-Fi 9-10
        PromptItem(
            id = "p49",
            globalNumber = 49,
            pageNumber = 13,
            category = "Futuristic & Sci-Fi",
            title = "Bioluminescent Cave",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 cinematic image of a natural cave with subtle bioluminescent organisms on wet rock walls, a shallow underground stream, and a lone explorer for scale. Keep the glow physically restrained and the geology believable. Rich textures, atmospheric depth, no fantasy creatures, no text or watermark."
        ),
        PromptItem(
            id = "p50",
            globalNumber = 50,
            pageNumber = 13,
            category = "Futuristic & Sci-Fi",
            title = "Robot Repair Bench",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 close-up of a technician repairing a compact humanoid service robot at a cluttered electronics bench. Show precise tools, exposed mechanical joints, circuit boards, realistic hands, and practical task lighting. Industrial documentary style, believable engineering, no extra fingers or limbs, no text or watermark."
        ),

        // Page 14: Food & Product Photography 1-8
        PromptItem(
            id = "p51",
            globalNumber = 51,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Luxury Burger Advertisement",
            type = "IMAGE PROMPT",
            text = "Create a premium photorealistic vertical 9:16 food-advertising photograph of a gourmet burger with a toasted brioche bun, crisp lettuce, ripe tomato, melted cheese, and a juicy patty. Add subtle steam, a dark textured tabletop, directional studio lighting, and appetizing realistic textures. Keep the burger physically plausible, no floating ingredients, no text or logo, no watermark."
        ),
        PromptItem(
            id = "p52",
            globalNumber = 52,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Artisan Coffee Pour",
            type = "IMAGE PROMPT",
            text = "Generate a macro 9:16 photograph of an expert barista pouring steamed milk into espresso, forming a precise rosetta latte-art pattern. Show realistic crema, ceramic cup texture, natural hand position, soft café window light, and shallow depth of field. Premium editorial food photography, no extra fingers, no text or watermark."
        ),
        PromptItem(
            id = "p53",
            globalNumber = 53,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Luxury Perfume Still Life",
            type = "IMAGE PROMPT",
            text = "Create a high-end vertical 9:16 product photograph of an elegant unbranded glass perfume bottle on polished stone, surrounded by translucent fabric, a few water droplets, and soft botanical shadows. Use controlled studio reflections, crisp glass edges, luxury editorial lighting, and clean negative space. No readable label, no logo, no watermark."
        ),
        PromptItem(
            id = "p54",
            globalNumber = 54,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Minimalist Sneakers",
            type = "IMAGE PROMPT",
            text = "Create a premium 9:16 product photograph of an unbranded contemporary sneaker on a sculptural concrete plinth. Show realistic knit fabric, stitching, rubber tread, soft shadow, and a neutral studio backdrop. Three-quarter product angle, high-detail commercial lighting, no distorted shape, no logo, no text, no watermark."
        ),
        PromptItem(
            id = "p55",
            globalNumber = 55,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Fresh Fruit Splash",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic vertical 9:16 commercial food image of fresh citrus fruit splashing into clear water. Freeze droplets naturally around sliced oranges, lemons, and limes, with crisp peel texture and bright diffused studio lighting. Accurate fruit anatomy and realistic fluid motion, no artificial plastic appearance, no text or watermark."
        ),
        PromptItem(
            id = "p56",
            globalNumber = 56,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Luxury Watch Macro",
            type = "IMAGE PROMPT",
            text = "Create a macro 9:16 product photograph of an elegant unbranded mechanical wristwatch on dark brushed metal. Show precise hands, realistic gears visible through a transparent caseback, polished steel reflections, and fine leather texture. Premium watch-ad lighting, physically accurate details, no invented readable brand, no text or watermark."
        ),
        PromptItem(
            id = "p57",
            globalNumber = 57,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Chocolate Dessert",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 dessert photograph of a rich chocolate mousse with glossy ganache, delicate cocoa dust, berries, and a small spoon on a ceramic plate. Use soft side lighting, shallow depth of field, believable food texture, fine crumbs, and premium restaurant styling. No floating garnish, no text or watermark."
        ),
        PromptItem(
            id = "p58",
            globalNumber = 58,
            pageNumber = 14,
            category = "Food & Product Photography",
            title = "Skincare Bottle with Water",
            type = "IMAGE PROMPT",
            text = "Create a clean premium 9:16 product photograph of an unbranded skincare bottle on a pale stone surface with beads of water and soft botanical shadows. Use elegant natural light, accurate glass or matte packaging, realistic reflections, balanced negative space, and crisp commercial detail. No claims or readable text, no logo, no watermark."
        ),

        // Page 15: Food & Product Photography 9-10
        PromptItem(
            id = "p59",
            globalNumber = 59,
            pageNumber = 15,
            category = "Food & Product Photography",
            title = "Steaming Ramen Bowl",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic 9:16 food photograph of a steaming ramen bowl with noodles, broth, egg, scallions, mushrooms, and chopsticks. Capture delicate steam against a dark background, realistic broth sheen, detailed ingredients, and warm restaurant light. Correct bowl geometry and appetizing natural color, no text or watermark."
        ),
        PromptItem(
            id = "p60",
            globalNumber = 60,
            pageNumber = 15,
            category = "Food & Product Photography",
            title = "Fresh Bread Bakery",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 bakery photograph of crusty sourdough loaves cooling on a wooden rack. Show flour dust, cracked crust, airy crumb on one cut loaf, warm window light, and a softly blurred artisan bakery background. Realistic textures, no plastic shine, no text or watermark."
        ),

        // Page 16: Fantasy & Surreal 1-8
        PromptItem(
            id = "p61",
            globalNumber = 61,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Floating Island Waterfall",
            type = "IMAGE PROMPT",
            text = "Create a cinematic photorealistic vertical 9:16 fantasy landscape of a massive floating island with a waterfall pouring into clouds below. Add mossy cliffs, ancient trees, distant mountain layers, and a tiny explorer for scale. Keep lighting, mist, and rock textures physically convincing despite the surreal premise. No text or watermark."
        ),
        PromptItem(
            id = "p62",
            globalNumber = 62,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Glass Treehouse",
            type = "IMAGE PROMPT",
            text = "Generate a dreamlike yet photorealistic 9:16 image of a glass treehouse built around an enormous ancient tree in a misty forest. Include curved walkways, warm interior lights, moss, ferns, and realistic structural supports. Premium fantasy-film production design, no cartoon look, no impossible floating furniture, no text or watermark."
        ),
        PromptItem(
            id = "p63",
            globalNumber = 63,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Giant Moon over Village",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 surreal landscape of a quiet rural village beneath an enormous luminous moon. Include stone houses, a narrow path, small lanterns, thin clouds, and a person standing in silhouette. Keep perspective coherent and shadows consistent, cinematic night photography, no text or watermark."
        ),
        PromptItem(
            id = "p64",
            globalNumber = 64,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Clockwork Garden",
            type = "IMAGE PROMPT",
            text = "Create an ultra-detailed 9:16 fantasy photograph of a secret garden where elegant brass clockwork mechanisms grow among roses and ivy. Include tiny gears, copper stems, dew, old stone paths, and golden morning light. Make materials physically realistic and intricate, no cartoon illustration, no text or watermark."
        ),
        PromptItem(
            id = "p65",
            globalNumber = 65,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Crystal Desert",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 surreal desert filled with giant translucent crystal formations rising from pale sand. Show realistic refraction, subtle color dispersion, distant dunes, and a lone traveler for scale. Natural low-angle sunlight, coherent shadows, detailed surfaces, no oversaturated glow, no text or watermark."
        ),
        PromptItem(
            id = "p66",
            globalNumber = 66,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Whale above Clouds",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic 9:16 surreal image of a majestic whale drifting through a sea of clouds above mountain peaks. Use realistic whale anatomy, soft atmospheric haze, sunlit cloud edges, and a small airship in the distance for scale. Dreamlike but photorealistic VFX, no cartoon style, no text or watermark."
        ),
        PromptItem(
            id = "p67",
            globalNumber = 67,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Enchanted Library",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 fantasy-film still of a vast ancient library with spiral staircases, towering wooden shelves, floating dust motes, and warm lamps. Include a tiny reader for scale and coherent architecture. Rich wood grain, cinematic shadows, realistic atmosphere, no floating books unless subtle and physically intentional, no text or watermark."
        ),
        PromptItem(
            id = "p68",
            globalNumber = 68,
            pageNumber = 16,
            category = "Fantasy & Surreal",
            title = "Mirror Lake Portal",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 landscape of a perfectly still mountain lake reflecting a natural stone arch that appears to open onto a starry sky. Keep the portal subtle, reflections physically coherent, and surrounding forest realistic. Twilight atmosphere, fine rock and water textures, no text or watermark."
        ),

        // Page 17: Fantasy & Surreal 9-10
        PromptItem(
            id = "p69",
            globalNumber = 69,
            pageNumber = 17,
            category = "Fantasy & Surreal",
            title = "Giant Flower Conservatory",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic vertical 9:16 image of a glass conservatory filled with enormous but botanically detailed flowers and tiny gardeners. Show delicate petals, dew, steel framing, misted glass, and sunlight beams. Surreal scale with believable textures and perspective, no cartoon look, no text or watermark."
        ),
        PromptItem(
            id = "p70",
            globalNumber = 70,
            pageNumber = 17,
            category = "Fantasy & Surreal",
            title = "Ancient Desert Temple",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic 9:16 cinematic image of an ancient sandstone temple half-buried in desert dunes. Include carved pillars, wind-eroded stairs, scattered pottery, and distant birds. Golden-hour shadows, plausible archaeological detail, weathered stone, epic but grounded composition, no readable inscriptions, no text or watermark."
        ),

        // Page 18: Travel & Culture 1-8
        PromptItem(
            id = "p71",
            globalNumber = 71,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Old City Market at Dawn",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 travel photograph of an old South Asian bazaar at dawn. Include shopkeepers opening wooden shutters, fruit carts, fabric stalls, old brick facades, and soft dust in the sunbeams. Candid documentary framing, culturally plausible details, realistic faces and hands, no stereotypes, no text or watermark."
        ),
        PromptItem(
            id = "p72",
            globalNumber = 72,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Mountain Pass Jeep",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic 9:16 travel photograph of a rugged 4x4 driving along a narrow mountain pass with dramatic cliffs and distant snowy peaks. Keep the road geometry believable, tires grounded, dust natural, and vehicle proportions accurate. Early morning light, documentary adventure style, no dangerous impossible driving, no text or watermark."
        ),
        PromptItem(
            id = "p73",
            globalNumber = 73,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Traditional Pottery Artisan",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 documentary photograph of a traditional potter shaping a clay vessel on a spinning wheel in a simple workshop. Show natural hand positions, wet clay, tools, shelves of finished pots, and directional window light. Accurate craft process, tactile detail, no extra fingers, no text or watermark."
        ),
        PromptItem(
            id = "p74",
            globalNumber = 74,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Rural Bread Oven",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 image of a rural artisan baking flatbread at a traditional clay oven outside a mud-and-wood house. Include flour on the work surface, stacked firewood, a few domestic animals in the background, and warm natural light. Respectful everyday-life documentary style, plausible anatomy and setting, no caricature, no text or watermark."
        ),
        PromptItem(
            id = "p75",
            globalNumber = 75,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Rainy Old Town Alley",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic vertical 9:16 street photograph of a historic old-town alley after rain. Include worn stone paving, small shops, hanging plants, warm window light, and subtle reflections. Use a natural eye-level perspective, realistic masonry and wet surfaces, no exaggerated fog, no readable signs, no watermark."
        ),
        PromptItem(
            id = "p76",
            globalNumber = 76,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Desert Tent Hospitality",
            type = "IMAGE PROMPT",
            text = "Create a respectful photorealistic vertical 9:16 cultural photograph of guests being welcomed into a traditional desert tent. Include woven rugs, cushions, a brass coffee pot, warm lantern light, and authentic fabric patterns. Natural expressions and hands, documentary composition, no stereotypes, no text or watermark."
        ),
        PromptItem(
            id = "p77",
            globalNumber = 77,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Coastal Fishing Village",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic 9:16 travel photograph of a small coastal fishing village at sunrise. Include colorful wooden boats, nets drying on posts, weathered cottages, gulls, and gentle waves. Accurate boat construction, realistic water reflections, warm natural light, editorial travel photography, no text or watermark."
        ),
        PromptItem(
            id = "p78",
            globalNumber = 78,
            pageNumber = 18,
            category = "Travel & Culture",
            title = "Ancient Cliffside Monastery",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic 9:16 photograph of a historic monastery perched on a rugged cliff. Show realistic stone construction, narrow pathways, distant mountains, drifting cloud, and soft early-morning light. Respectful travel-documentary style, plausible scale, no fantasy additions, no text or watermark."
        ),

        // Page 19: Travel & Culture 9-10
        PromptItem(
            id = "p79",
            globalNumber = 79,
            pageNumber = 19,
            category = "Travel & Culture",
            title = "Night Festival Lanterns",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 photograph of a community lantern festival in a historic street. Show warm lanterns overhead, people in natural candid poses, detailed traditional clothing, and a softly glowing evening atmosphere. Keep faces anatomically natural and crowd density believable, no text overlays, no watermark."
        ),
        PromptItem(
            id = "p80",
            globalNumber = 80,
            pageNumber = 19,
            category = "Travel & Culture",
            title = "Snowy Village Window Glow",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic 9:16 winter travel photograph of a small mountain village at blue hour. Snow-covered roofs, smoke from chimneys, warm window glow, footprints in fresh snow, and pine-covered hills create a cozy atmosphere. Realistic snow physics, natural color, no fantasy glow, no text or watermark."
        ),

        // Page 20: Technology & Gadgets 1-8
        PromptItem(
            id = "p81",
            globalNumber = 81,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Transparent Headphones",
            type = "IMAGE PROMPT",
            text = "Create a premium vertical 9:16 product image of futuristic but plausible transparent-shell headphones on a clean acrylic stand. Show realistic drivers, fine mesh, hinges, subtle reflections, and a neutral gradient studio background. Accurate product proportions, commercial lighting, no logos, no text, no watermark."
        ),
        PromptItem(
            id = "p82",
            globalNumber = 82,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Foldable Phone Concept",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 product-render photograph of a premium foldable smartphone in half-open position on a stone plinth. Show believable hinge geometry, thin bezels, realistic glass reflections, subtle screen gradients without readable UI, and refined studio lighting. No brand logo, no text, no impossible screen bends, no watermark."
        ),
        PromptItem(
            id = "p83",
            globalNumber = 83,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Mechanical Keyboard Macro",
            type = "IMAGE PROMPT",
            text = "Create a macro 9:16 product photograph of a custom mechanical keyboard with sculpted keycaps, brushed aluminum frame, subtle RGB underglow, and a clean desk setup. Show crisp legends that are generic and legible or omit them, realistic shadows and key spacing, premium technology editorial style, no logos, no watermark."
        ),
        PromptItem(
            id = "p84",
            globalNumber = 84,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Desktop Creator Setup",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 image of a polished creator workstation with dual monitors, microphone, camera, compact lights, headphones, and an organized desk. Screens show abstract non-readable visuals. Balanced warm ambient light, realistic cable routing, modern but attainable equipment, no brand logos, no text or watermark."
        ),
        PromptItem(
            id = "p85",
            globalNumber = 85,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Mini Drone on Rooftop",
            type = "IMAGE PROMPT",
            text = "Generate a realistic 9:16 product photograph of a compact camera drone resting on a rooftop ledge at sunrise, with a city skyline in soft focus. Show accurate propellers, sensors, battery seams, and natural material texture. Keep all propellers stationary and safely grounded, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p86",
            globalNumber = 86,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Smartwatch Fitness Scene",
            type = "IMAGE PROMPT",
            text = "Create a premium vertical 9:16 product photograph of an unbranded smartwatch on a person's wrist during an outdoor run. Show a realistic strap fit, natural skin, screen with abstract non-readable graphics, and sunlit park bokeh. Correct wrist anatomy, commercial editorial lighting, no brand logo, no watermark."
        ),
        PromptItem(
            id = "p87",
            globalNumber = 87,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Compact Camera Still Life",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 product image of an unbranded mirrorless camera with a lens, battery, and memory card arranged on a dark tabletop. Show realistic lens coatings, metal controls, rubber grip, and soft directional light. Accurate camera geometry, no fake logos or readable text, no watermark."
        ),
        PromptItem(
            id = "p88",
            globalNumber = 88,
            pageNumber = 20,
            category = "Technology & Gadgets",
            title = "Portable Speaker in Nature",
            type = "IMAGE PROMPT",
            text = "Generate a premium vertical 9:16 product photograph of a rugged unbranded portable speaker on a smooth rock beside a mountain lake. Show fabric grille, rubberized edges, realistic droplets, and a misty landscape. Natural outdoor lighting, true scale, no logos, no text or watermark."
        ),

        // Page 21: Technology & Gadgets 9-10
        PromptItem(
            id = "p89",
            globalNumber = 89,
            pageNumber = 21,
            category = "Technology & Gadgets",
            title = "Electric Bike Detail",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 close-up of a modern electric bicycle parked against a minimalist urban wall. Emphasize frame welds, cable routing, tire tread, disc brakes, and battery integration. Natural side light, commercial product photography, realistic proportions, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p90",
            globalNumber = 90,
            pageNumber = 21,
            category = "Technology & Gadgets",
            title = "Robotic Vacuum in Modern Home",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic 9:16 lifestyle product photograph of a compact robotic vacuum navigating a bright modern living room. Show realistic furniture clearance, hardwood floor reflections, dust-free surfaces, and soft natural light. Plausible product scale, no floating parts, no logos, no text or watermark."
        ),

        // Page 22: Vehicles & Motion 1-8
        PromptItem(
            id = "p91",
            globalNumber = 91,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Electric Supercar at Night",
            type = "IMAGE PROMPT",
            text = "Create a cinematic vertical 9:16 automotive photograph of a fictional electric supercar parked on a wet city street at night. Sculpted body panels, realistic tire contact, precise reflections, dramatic architectural lighting, and low three-quarter camera angle. No real brand badges, no impossible wheel geometry, no text or watermark."
        ),
        PromptItem(
            id = "p92",
            globalNumber = 92,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Classic Car in Countryside",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 photograph of a restored classic car parked on a quiet country lane with rolling hills and hedgerows. Show realistic chrome, aged paint, accurate tires, soft morning mist, and natural sunlight. Editorial automotive photography, no visible brand logos, no text or watermark."
        ),
        PromptItem(
            id = "p93",
            globalNumber = 93,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Motorcycle on Mountain Road",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 action photograph of a fictional motorcycle leaning safely through a sweeping mountain road curve. Rider wears complete protective gear; tires remain in believable contact with the road. Dramatic scenery, motion blur in the background, sharp bike details, no reckless traffic, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p94",
            globalNumber = 94,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Vintage Train in Snow",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 photograph of a vintage train moving slowly through a snowy forest. Show accurate locomotive structure, warm carriage windows, snow-covered rails, gentle steam, and distant pines. Cinematic winter light, coherent rail alignment, no duplicated carriages, no text or watermark."
        ),
        PromptItem(
            id = "p95",
            globalNumber = 95,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Off-Road Truck Splash",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic 9:16 automotive action image of a rugged off-road truck crossing a shallow muddy puddle on a forest track. Freeze realistic water droplets and tire spray, with mud on the lower panels and a natural overcast sky. Correct wheel count and vehicle proportions, no dangerous human proximity, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p96",
            globalNumber = 96,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Airplane above Clouds",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 aviation photograph of a passenger airplane cruising above a sea of clouds at sunrise. Show accurate wing geometry, engine placement, natural atmospheric haze, and warm light on the fuselage. Telephoto aviation-photography style, no airline logos, no text or watermark."
        ),
        PromptItem(
            id = "p97",
            globalNumber = 97,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Futuristic Train Platform",
            type = "IMAGE PROMPT",
            text = "Create a realistic 9:16 concept photograph of a sleek high-speed train at a modern station platform. Include plausible doors, platform safety markings, overhead structures, and passengers at a safe distance. Clean architectural lines, reflective metal, cool daylight, no distorted train shape, no readable signage, no watermark."
        ),
        PromptItem(
            id = "p98",
            globalNumber = 98,
            pageNumber = 22,
            category = "Vehicles & Motion",
            title = "Bicycle Through Autumn",
            type = "IMAGE PROMPT",
            text = "Generate a cinematic vertical 9:16 action photograph of a cyclist riding along a leaf-covered park path in autumn. Show a correctly positioned rider, realistic wheel rotation, natural clothing folds, and leaves scattering behind. Warm backlight, motion in the background, no unsafe road behavior, no text or watermark."
        ),

        // Page 23: Vehicles & Motion 9
        PromptItem(
            id = "p99",
            globalNumber = 99,
            pageNumber = 23,
            category = "Vehicles & Motion",
            title = "Sailboat at Golden Hour",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic 9:16 photograph of a sailboat gliding across calm water at golden hour. Show taut sails, believable rigging, accurate reflections, a distant coastline, and gentle wake. Warm cinematic light, balanced composition, no impossible wind direction, no text or watermark."
        ),

        // Page 24: Social Media & Creator 1-8
        PromptItem(
            id = "p100",
            globalNumber = 100,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Creator Recording a Reel",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 behind-the-scenes image of a content creator recording a short reel in a tidy home studio. Include a smartphone on a tripod, small key light, microphone, and natural hand gestures. Show a believable recording setup, realistic screen glow, expressive but natural face, no platform logos, no text or watermark."
        ),
        PromptItem(
            id = "p101",
            globalNumber = 101,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Podcast Conversation",
            type = "IMAGE PROMPT",
            text = "Generate an ultra-realistic vertical 9:16 photograph of two fictional hosts recording a podcast across a small table. Include two microphones, headphones, a laptop, acoustic panels, and warm practical lights. Capture natural conversational gestures and believable eye lines, documentary style, no extra fingers, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p102",
            globalNumber = 102,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Thumbnail Creator at Desk",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 image of a creator designing a video thumbnail at a computer. Show the person from a three-quarter angle, a monitor with abstract colorful blocks but no readable text, a tablet, pen, and soft studio lighting. Realistic hands and screen reflections, clean workspace, no logos, no watermark."
        ),
        PromptItem(
            id = "p103",
            globalNumber = 103,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Vertical Video Set",
            type = "IMAGE PROMPT",
            text = "Create a premium behind-the-scenes 9:16 photograph of a compact vertical-video set with a seated presenter, softbox, fill light, microphone, and neutral backdrop. Show practical light stands and realistic shadows, a candid production moment, accurate equipment proportions, no brand logos, no text or watermark."
        ),
        PromptItem(
            id = "p104",
            globalNumber = 104,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Creator Portrait with Camera",
            type = "IMAGE PROMPT",
            text = "Create a polished photorealistic 9:16 portrait of a fictional content creator holding a mirrorless camera in a modern studio. Use soft directional light, realistic skin texture, subtle background bokeh, naturally positioned fingers around the camera grip, and a confident but relaxed expression. No logos, no text, no watermark."
        ),
        PromptItem(
            id = "p105",
            globalNumber = 105,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Editing Timeline Workspace",
            type = "IMAGE PROMPT",
            text = "Generate a photorealistic vertical 9:16 image of a video editor working at a dual-monitor workstation. Screens display a generic editing timeline with colored clips and waveforms but no readable brand names. Include headphones, control surface, desk lamp, and realistic reflections. Professional creator atmosphere, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p106",
            globalNumber = 106,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Outdoor Travel Vlogger",
            type = "IMAGE PROMPT",
            text = "Create a cinematic 9:16 photograph of a fictional travel vlogger filming a mountain viewpoint with a compact camera and small microphone. Show wind moving the jacket naturally, dramatic valley layers, safe footing, and realistic morning light. Authentic travel-documentary style, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p107",
            globalNumber = 107,
            pageNumber = 24,
            category = "Social Media & Creator",
            title = "Food Reel Setup",
            type = "IMAGE PROMPT",
            text = "Create a photorealistic vertical 9:16 behind-the-scenes image of a food creator filming a freshly prepared meal on a tabletop. Include a phone mounted overhead, small LED panel, utensils, ingredients, and natural window light. Realistic hand positions and food textures, coherent camera rig, no text or watermark."
        ),

        // Page 25: Social Media & Creator 9-10
        PromptItem(
            id = "p108",
            globalNumber = 108,
            pageNumber = 25,
            category = "Social Media & Creator",
            title = "Home Photo Studio",
            type = "IMAGE PROMPT",
            text = "Generate a realistic 9:16 photograph of a small home photo studio with seamless paper backdrop, softboxes, reflector, camera tripod, and a stool. Show a photographer adjusting a light stand. Neat but lived-in environment, accurate equipment, soft shadows, no logos, no text or watermark."
        ),
        PromptItem(
            id = "p109",
            globalNumber = 109,
            pageNumber = 25,
            category = "Social Media & Creator",
            title = "Creative Team Brainstorm",
            type = "IMAGE PROMPT",
            text = "Create an ultra-realistic vertical 9:16 image of a small creative team brainstorming around a table covered with notebooks, color swatches, a camera, and a laptop. Natural collaborative gestures, believable expressions, bright studio daylight, editorial documentary photography, no readable writing, no logos, no watermark."
        ),

        // Page 26: Motion & Video Prompts 1-7
        PromptItem(
            id = "p110",
            globalNumber = 110,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "London Miniature Build — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 10–15 second ultra-realistic vertical 9:16 video inside a miniature-making workshop. Start with a macro shot of a fine brush painting a tiny red double-decker bus, then slowly pull back to reveal the handcrafted London diorama and the artist leaning over the bench. The artist places one final miniature tree, adjusts Tower Bridge with tweezers, and looks satisfied. Use smooth camera movement, physically consistent miniature scale, natural hand motion, warm desk-lamp lighting, subtle workshop ambience, and crisp macro focus. No jump cuts that change object positions, no morphing buildings, no extra fingers, no captions, no watermark."
        ),
        PromptItem(
            id = "p111",
            globalNumber = 111,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Product Reveal — Video",
            type = "VIDEO PROMPT",
            text = "■ Generate a 8–12 second vertical 9:16 premium product-reveal video of an unbranded glass perfume bottle on a dark stone plinth. Begin in near darkness, slowly sweep a narrow studio light across the glass, let condensation catch highlights, and make a gentle 120-degree camera orbit. Keep the bottle shape and reflections consistent in every frame. Smooth motion, controlled highlights, realistic materials, no text, no logo, no watermark."
        ),
        PromptItem(
            id = "p112",
            globalNumber = 112,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Wildlife Slow Motion — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 10-second vertical 9:16 wildlife video of a kingfisher diving into a clear stream and emerging with droplets scattering. Use one continuous telephoto shot, natural wing and body motion, accurate bird anatomy, realistic water physics, and a stable background. Slow motion only at the splash moment, then return to real-time. No morphing, no extra wings, no captions, no watermark."
        ),
        PromptItem(
            id = "p113",
            globalNumber = 113,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Creator Talking to Camera — Video",
            type = "VIDEO PROMPT",
            text = "■ Generate a 12–15 second vertical 9:16 video of a fictional content creator speaking directly to camera in a home studio. The creator delivers this line naturally: “Here’s one simple trick that makes your videos look more professional.” Include accurate lip-sync to the exact sentence, natural blinking, small hand gestures, subtle breathing, and a steady camera. Maintain consistent face, clothing, lighting, and background throughout. No subtitles unless requested, no voice changes mid-sentence, no watermark."
        ),
        PromptItem(
            id = "p114",
            globalNumber = 114,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Rainy Supercar Drive-By — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 8–10 second cinematic vertical 9:16 video of a fictional electric supercar driving past the camera on a wet city street at night. Start with reflections on the pavement, then the car enters frame, passes smoothly, and leaves realistic tire spray. Keep wheel rotation, body geometry, headlights, and reflections consistent. Low tracking camera, practical neon lighting, no dangerous pedestrians, no logo, no text, no watermark."
        ),
        PromptItem(
            id = "p115",
            globalNumber = 115,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Mountain Travel Reveal — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 10–12 second vertical 9:16 travel video. Begin behind a hiker on a narrow safe viewpoint path, then smoothly crane upward to reveal a huge mountain valley at sunrise. Wind moves clothing and grasses naturally; clouds drift slowly; the hiker remains anatomically consistent. Cinematic lens flare kept subtle, realistic atmospheric depth, no sudden landscape changes, no captions, no watermark."
        ),
        PromptItem(
            id = "p116",
            globalNumber = 116,
            pageNumber = 26,
            category = "Motion & Video Prompts",
            title = "Coffee Pour — Video",
            type = "VIDEO PROMPT",
            text = "■ Generate an 8-second vertical 9:16 macro video of a barista pouring milk into espresso to create a rosetta latte-art pattern. Show a steady hand, believable milk flow, crema moving naturally, and steam drifting upward. One continuous close-up, shallow depth of field, warm café lighting, no cup or hand morphing, no text, no watermark."
        ),

        // Page 27: Motion & Video Prompts 8-10
        PromptItem(
            id = "p117",
            globalNumber = 117,
            pageNumber = 27,
            category = "Motion & Video Prompts",
            title = "Room Transformation — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 10-second vertical 9:16 room-transformation video in one fixed camera angle. Begin with a plain empty living room; furniture and decor are brought in by people in realistic time-lapse motion until the room becomes a warm minimalist space. Preserve room geometry, windows, and door positions in every frame. No objects popping into existence, no warped walls, no text or watermark."
        ),
        PromptItem(
            id = "p118",
            globalNumber = 118,
            pageNumber = 27,
            category = "Motion & Video Prompts",
            title = "Robot Assembly — Video",
            type = "VIDEO PROMPT",
            text = "■ Generate a 10–12 second vertical 9:16 close-up video of a technician assembling a compact service robot at a workbench. Show a screwdriver fastening a panel, a cable being connected, and a small status light turning on. Keep all mechanical parts consistent and movements physically plausible. Stable macro camera, realistic tool contact, no extra fingers, no shape-changing robot, no text or watermark."
        ),
        PromptItem(
            id = "p119",
            globalNumber = 119,
            pageNumber = 27,
            category = "Motion & Video Prompts",
            title = "Funny Village Chase — Video",
            type = "VIDEO PROMPT",
            text = "■ Create a 12–15 second comedic vertical 9:16 video in a rural South Asian village lane. A fictional man in slightly worn traditional clothes jogs after a mischievous hen; the hen darts around a basket, the man slips harmlessly on loose straw and sits down in surprise, while the hen confidently struts away. Use realistic body mechanics, clear continuous movement, a wide shot followed by a medium shot, natural village ambience, playful sound effects, and consistent faces/clothing. No injury, no dangerous fall, no sudden character changes, no captions, no watermark."
        )
    )

    val pages: List<BookPage> = buildPages()

    private fun buildPages(): List<BookPage> {
        val list = mutableListOf<BookPage>()

        // Page 1: Cover
        list.add(
            BookPage(
                pageNumber = 1,
                title = "The Modern AI Prompt Book",
                category = "Cover",
                pageType = PageType.Cover
            )
        )

        // Page 2: How to Use & Contents (Part 1)
        list.add(
            BookPage(
                pageNumber = 2,
                title = "How to Use This Book & Contents (I)",
                category = "Guide & Index",
                pageType = PageType.HowToAndToc1
            )
        )

        // Page 3: Contents (Part 2)
        list.add(
            BookPage(
                pageNumber = 3,
                title = "Table of Contents (II)",
                category = "Index",
                pageType = PageType.Toc2
            )
        )

        // Pages 4 to 27: Prompts
        for (p in 4..27) {
            val pagePrompts = allPrompts.filter { it.pageNumber == p }
            val catName = pagePrompts.firstOrNull()?.category ?: "Prompts"
            list.add(
                BookPage(
                    pageNumber = p,
                    title = "$catName (Page $p)",
                    category = catName,
                    pageType = PageType.Prompts(catName),
                    prompts = pagePrompts
                )
            )
        }

        // Page 28: Quick Customization Checklist
        list.add(
            BookPage(
                pageNumber = 28,
                title = "Quick Customization Checklist",
                category = "Checklist & Tips",
                pageType = PageType.Checklist
            )
        )

        return list
    }
}
