# CSSE3030 Week 11 Practical: Security Testing and Fuzzing

You fuzz three small components with a hand-written random fuzzer, a hand-written
mutation fuzzer, and [Jazzer](https://github.com/CodeIntelligenceTesting/jazzer)
(coverage-guided fuzzing that runs as JUnit tests).

| Component | Part | What it does |
|---|---|---|
| `TicketParser` | 2 and 5 | Parses `TKT1:<holder>:<quantity>` |
| `UrlCodec` | 3 | Decodes `%XX` and `+` escapes |
| `StudentDao` | 4 | Looks up a student by id in an in-memory H2 database |

Each class has its **contract in its Javadoc**. Work from the contract, not from the
source: your tests and harnesses should say what the code *should* do. The worksheet
tells you which files to write. They are empty to start with (look for `TODO`).

## Requirements

- JDK 17 or newer (`java -version`)
- Nothing else to install: use the Gradle wrapper (`./gradlew`) included in the repo
- Internet access to Maven Central the first time you build (Gradle downloads JUnit, Jazzer and H2)

## Get the code

```bash
git clone https://github.com/mferr11/CSSE3030PracticalWeek11.git
cd CSSE3030PracticalWeek11
./gradlew build -x test
```

The last command must end with `BUILD SUCCESSFUL`. On Windows PowerShell or cmd use
`.\gradlew` instead of `./gradlew`.

## Running

| Command | Part | Runs |
|---|---|---|
| `./gradlew testQ2` | 2 | `RandomFuzzerTest`, `MutationFuzzerTest`, `TicketParserRegressionTest` (plain JUnit, seconds) |
| `./gradlew testQ3` | 3 | `UrlCodecFuzzTest`, `UrlCodecRegressionTest` (Jazzer, up to about 60 s) |
| `./gradlew testQ4` | 4 | `StudentDaoTest`, `StudentDaoFuzzTest` (unit tests, then Jazzer, up to about 60 s) |
| `./gradlew testQ5` | 5 | `TicketParserFuzzTest`, `TicketParserRegressionTest` (Jazzer, up to about 60 s) |
| `./gradlew test` | any | Everything, but Jazzer only **replays saved inputs** (fast, no new fuzzing) |

Part 1 is on paper and has no task.

### What a pass and a finding look like

- **Pass:** `BUILD SUCCESSFUL`, and each test listed as `PASSED`. A Jazzer test that
  fuzzed for the full time without finding anything also ends as `PASSED`
  (look for `Done ... runs in 61 second(s)`).
- **Finding:** the task fails with `BUILD FAILED`, and the test shows `FAILED` with the
  exception Jazzer found, for example
  `FuzzTestFindingException: java.lang.StringIndexOutOfBoundsException`, followed by a
  `Caused by:` stack trace that points into the code under test. Jazzer also prints the
  failing input, as text and as Base64, just above the failure.
- The full report for a run is in `build/reports/tests/<task>/index.html`.

A fuzzing task re-runs every time and stops by itself after about 60 seconds, because your
harness is annotated `@FuzzTest(maxDuration = "60s")`. Without `maxDuration`, Jazzer would fuzz
for 5 minutes, so the Gradle fuzzing tasks give up after 3 minutes with
`Timeout has been exceeded`. If you see that, check your `@FuzzTest` annotation.

## Where Jazzer saves what it finds

When Jazzer finds a failing input it writes it to a file named `crash-<hash>`:

- on a fresh clone, into the **project folder** (next to `build.gradle`);
- if the folder `src/test/resources` already exists, straight into
  `src/test/resources/csse3030/week11/<TestClass>Inputs/<testMethod>/`
  (Jazzer creates the sub-folders for you).

That second folder is Jazzer's **regression input folder**, for example
`src/test/resources/csse3030/week11/UrlCodecFuzzTestInputs/decodeNeverThrowsUnexpectedly/`.
**Every file in it is replayed as a test case in every run**, including a plain
`./gradlew test`. A `crash-...` file sitting in the project folder is *not* replayed.

To keep a finding as a regression input (Q3.4), move it into the folder for that test:

```bash
mkdir -p src/test/resources/csse3030/week11/UrlCodecFuzzTestInputs/decodeNeverThrowsUnexpectedly
mv crash-<hash> src/test/resources/csse3030/week11/UrlCodecFuzzTestInputs/decodeNeverThrowsUnexpectedly/
```

(Use your own `<hash>`, and the `<TestClass>Inputs/<testMethod>` names of the test you are
working on. In PowerShell use `New-Item -ItemType Directory -Force <folder>` and `Move-Item`.)

You can also add an input of your own by creating a file there: the file's bytes are the
input. For example, for the input `abc%`:

```bash
printf 'abc%%' > src/test/resources/csse3030/week11/UrlCodecFuzzTestInputs/decodeNeverThrowsUnexpectedly/truncated-escape
```

(PowerShell: `Set-Content -NoNewline -Encoding ascii -Path <that file> -Value 'abc%'`)

Once an input is saved there:

- until you fix the bug, `./gradlew test` fails on it (a regression test doing its job);
- after you fix it, it passes and keeps protecting you.

If a saved input is failing and you no longer want it, delete that one file. Jazzer's
scratch corpus in `.cifuzz-corpus/` is safe to delete at any time. Neither it nor the
`crash-...` files in the project folder are committed (see `.gitignore`).

## Seed inputs (Q5.2) and comparing runs

The same `<TestClass>Inputs/<testMethod>/` folder doubles as the **seed corpus**: every file
in it is also fed to the fuzzer as a starting input when you run a `testQ` task. To seed
`TicketParserFuzzTest`, create a file whose bytes are the seed, for example `TKT1:alice:2`:

```bash
mkdir -p src/test/resources/csse3030/week11/TicketParserFuzzTestInputs/parseOnlyThrowsParseException
printf 'TKT1:alice:2' > src/test/resources/csse3030/week11/TicketParserFuzzTestInputs/parseOnlyThrowsParseException/valid-ticket
```

Jazzer also keeps what it learns in `.cifuzz-corpus/` and **reuses it on the next run**. To
compare a run without a seed to a run with one, delete `.cifuzz-corpus/` (and any seed file)
before each run, otherwise the second run starts from everything the first one learned.

## If you fall behind

If you did not finish a Part, you can load the fixed source for the earlier Parts and
carry on. Run **one** of these from the project folder:

```bash
git checkout checkpoint-after-part2 -- src/main   # TicketParser fixed (end of Part 2)
git checkout checkpoint-after-part3 -- src/main   # TicketParser and UrlCodec fixed (end of Part 3)
```

Each command replaces only the files under `src/main` with the versions from that
checkpoint. Your tests under `src/test` are not touched. (Run `git diff src/main` to see
what changed; `git checkout main -- src/main` puts back the original code.)

## Getting unstuck

| Problem | Try |
|---|---|
| `BUILD FAILED` before any test runs | Read the first error. A missing `;` or class name is a compile error in your file |
| Not sure what you changed | `git status` and `git diff` |
| Want to undo your edits to one file | `git checkout -- path/to/File.java` (this discards your edits to it) |
| Gradle behaves strangely | `./gradlew clean`, then run the task again |
| Download errors on first run | Check you have internet access to Maven Central and try again |
| A fuzz run seems stuck | Wait: it stops after about 60 s (3 minutes at most). Press Ctrl+C if you must |
| `Filename too long` when cloning (Windows) | Clone into a short path such as `C:\code\`, or run `git config --global core.longpaths true` |
| Still stuck | Ask your tutor |
