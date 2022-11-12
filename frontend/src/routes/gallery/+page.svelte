<script>
	import axios from 'axios';
	import { onMount } from 'svelte';

	let photos = [];

	onMount(async () => {
		const res = await axios.get('https://finalspaceapi.com/api/v0/character/?limit=2');
		photos = await res.data;
	});
</script>

<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<div class="text-column">
	<a href="/gallery/post">Post</a>
</div>
